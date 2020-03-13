package test;

import java.util.OptionalLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {

    public static Integer truth(Integer ok) {
        return 0;
    }


    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(Long::sum).get();
    }

    public static long parallelSum(long n) {
        Long aLong = Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(Long::sum).get();
        return aLong;
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(Long::sum).getAsLong();
    }

    public static long parallelRangedSum(long n) {
        OptionalLong reduce = LongStream.rangeClosed(1, n).parallel().reduce(Long::sum);
        long l = LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
        long a = 1;
        Long b = 1L;
        return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).getAsLong();
    }

    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    public static class Accumulator {
        private long total = 0;

        public void add(long value) {
            total += value;
        }
    }
}
