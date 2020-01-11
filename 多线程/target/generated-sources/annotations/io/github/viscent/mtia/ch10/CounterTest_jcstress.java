package io.github.viscent.mtia.ch10;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.openjdk.jcstress.infra.runners.TestConfig;
import org.openjdk.jcstress.infra.collectors.TestResultCollector;
import org.openjdk.jcstress.infra.runners.Runner;
import org.openjdk.jcstress.infra.runners.StateHolder;
import org.openjdk.jcstress.util.Counter;
import org.openjdk.jcstress.vm.WhiteBoxSupport;
import org.openjdk.jcstress.util.OpenAddressHashCounter;
import java.util.concurrent.ExecutionException;
import io.github.viscent.mtia.ch10.CounterTest;
import org.openjdk.jcstress.infra.results.LongResult1_jcstress;
import io.github.viscent.mtia.ch10.CounterTest_StateObject_jcstress;

public class CounterTest_jcstress extends Runner<LongResult1_jcstress> {

    OpenAddressHashCounter<LongResult1_jcstress> counter_actor1;
    OpenAddressHashCounter<LongResult1_jcstress> counter_actor2;
    CounterTest test;
    volatile StateHolder<Pair> version;

    public CounterTest_jcstress(TestConfig config, TestResultCollector collector, ExecutorService pool) {
        super(config, collector, pool, "io.github.viscent.mtia.ch10.CounterTest");
    }

    @Override
    public void sanityCheck() throws Throwable {
        sanityCheck_API();
        sanityCheck_Footprints();
    }

    private void sanityCheck_API() throws Throwable {
        final CounterTest t = new CounterTest();
        final CounterTest_StateObject_jcstress s = new CounterTest_StateObject_jcstress();
        final LongResult1_jcstress r = new LongResult1_jcstress();
        Collection<Future<?>> res = new ArrayList<>();
        res.add(pool.submit(() -> t.actor1(s)));
        res.add(pool.submit(() -> t.actor2(s)));
        for (Future<?> f : res) {
            try {
                f.get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        }
        t.actor3(r, s);
    }

    private void sanityCheck_Footprints() throws Throwable {
        config.adjustStrides(size -> {
            version = new StateHolder<>(new Pair[size], 2, config.spinLoopStyle);
            final CounterTest t = new CounterTest();
            for (int c = 0; c < size; c++) {
                Pair p = new Pair();
                p.r = new LongResult1_jcstress();
                p.s = new CounterTest_StateObject_jcstress();
                version.pairs[c] = p;
                t.actor1(p.s);
                t.actor2(p.s);
            }
        });
    }

    @Override
    public Counter<LongResult1_jcstress> internalRun() {
        test = new CounterTest();
        version = new StateHolder<>(new Pair[0], 2, config.spinLoopStyle);
        counter_actor1 = new OpenAddressHashCounter<>();
        counter_actor2 = new OpenAddressHashCounter<>();

        control.isStopped = false;
        Collection<Future<?>> tasks = new ArrayList<>();
        tasks.add(pool.submit(this::actor1));
        tasks.add(pool.submit(this::actor2));

        try {
            TimeUnit.MILLISECONDS.sleep(config.time);
        } catch (InterruptedException e) {
        }

        control.isStopped = true;

        waitFor(tasks);

        Counter<LongResult1_jcstress> counter = new OpenAddressHashCounter<>();
        counter.merge(counter_actor1);
        counter.merge(counter_actor2);
        return counter;
    }

    public final void jcstress_consume(StateHolder<Pair> holder, OpenAddressHashCounter<LongResult1_jcstress> cnt, int a, int actors) {
        Pair[] pairs = holder.pairs;
        int len = pairs.length;
        int left = a * len / actors;
        int right = (a + 1) * len / actors;
        for (int c = left; c < right; c++) {
            Pair p = pairs[c];
            LongResult1_jcstress r = p.r;
            CounterTest_StateObject_jcstress s = p.s;
            test.actor3(r, s);
            p.s = new CounterTest_StateObject_jcstress();
            cnt.record(r);
            r.r1 = 0;
        }
    }

    public final void jcstress_updateHolder(StateHolder<Pair> holder) {
        if (!holder.tryStartUpdate()) return;
        Pair[] pairs = holder.pairs;
        int len = pairs.length;

        int newLen = holder.updateStride ? Math.max(config.minStride, Math.min(len * 2, config.maxStride)) : len;

        Pair[] newPairs = pairs;
        if (newLen > len) {
            newPairs = Arrays.copyOf(pairs, newLen);
            for (int c = len; c < newLen; c++) {
                Pair p = new Pair();
                p.r = new LongResult1_jcstress();
                p.s = new CounterTest_StateObject_jcstress();
                newPairs[c] = p;
            }
         }

        version = new StateHolder<>(control.isStopped, newPairs, 2, config.spinLoopStyle);
        holder.finishUpdate();
   }

    public final Void actor1() {

        CounterTest lt = test;
        OpenAddressHashCounter<LongResult1_jcstress> counter = counter_actor1;
        while (true) {
            StateHolder<Pair> holder = version;
            if (holder.stopped) {
                return null;
            }

            Pair[] pairs = holder.pairs;

            holder.preRun();

            for (Pair p : pairs) {
                CounterTest_StateObject_jcstress s = p.s;
                s.trap = 0;
                lt.actor1(s);
            }

            holder.postRun();

            jcstress_consume(holder, counter, 0, 2);
            jcstress_updateHolder(holder);

            holder.postUpdate();
        }
    }

    public final Void actor2() {

        CounterTest lt = test;
        OpenAddressHashCounter<LongResult1_jcstress> counter = counter_actor2;
        while (true) {
            StateHolder<Pair> holder = version;
            if (holder.stopped) {
                return null;
            }

            Pair[] pairs = holder.pairs;

            holder.preRun();

            for (Pair p : pairs) {
                CounterTest_StateObject_jcstress s = p.s;
                s.trap = 0;
                lt.actor2(s);
            }

            holder.postRun();

            jcstress_consume(holder, counter, 1, 2);
            jcstress_updateHolder(holder);

            holder.postUpdate();
        }
    }

    static class Pair {
        public CounterTest_StateObject_jcstress s;
        public LongResult1_jcstress r;
    }
}
