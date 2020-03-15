package lambdasinaction.chap6;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static java.util.stream.Collectors.partitioningBy;

public class PartitionPrimeNumbers {

    public static void main(String... args) {
        System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimes(100));
        System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimesWithCustomCollector(100));

        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
        List<Integer> numbers = stream.reduce(
                new ArrayList<Integer>(),
                (List<Integer> l, Integer e) -> {
                    l.add(e);
                    return l;
                },
                (List<Integer> l1, List<Integer> l2) -> {
                    l1.addAll(l2);
                    return l1;
                });
    }


    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        Map<Boolean, List<Integer>> collect = IntStream.rangeClosed(2, n).boxed()
                .collect(partitioningBy(candidate -> isPrime(candidate)));
        return collect;
    }

    public static boolean isPrime(int candidate) {
        boolean match = IntStream.rangeClosed(2, candidate - 1)
                .limit((long) Math.floor(Math.sqrt(candidate)) - 1)
                .noneMatch(i -> candidate % i == 0);
        return match;
    }

    /**
     * 检测一个数是否是质数
     *
     * @param candidate 被检测的数
     * @return true:是质数;false:不是
     */
    public static boolean isPrimeSimple(int candidate) {
        return IntStream.rangeClosed(2, candidate - 1)
                .noneMatch(i -> candidate % i == 0);
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(new PrimeNumbersCollector());
    }

    public static boolean isPrime(List<Integer> primes, Integer candidate) {
        double candidateRoot = Math.sqrt((double) candidate);
        boolean match = IntStream.rangeClosed(2, candidate)
                .noneMatch(i -> candidate % i == 0);

        // return takeWhile(primes, i -> i <= candidateRoot).stream().noneMatch(i -> candidate % i == 0);
        // return primes.stream().takeWhile(i -> i <= candidateRoot).noneMatch(i -> candidate % i == 0);
        return match;
    }

    public Map<Boolean, List<Integer>> partitionPrimesWithInlineCollector(int n) {
        return Stream.iterate(2, i -> i + 1).limit(n)
                .collect(
                        () -> new HashMap<Boolean, List<Integer>>() {{
                            put(true, new ArrayList<Integer>());
                            put(false, new ArrayList<Integer>());
                        }},
                        (acc, candidate) -> {
                            acc.get(isPrime(acc.get(true), candidate))
                                    .add(candidate);
                        },
                        (map1, map2) -> {
                            map1.get(true).addAll(map2.get(true));
                            map1.get(false).addAll(map2.get(false));
                        });
    }

    /*    *//*
        public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
            int i = 0;
            for (A item : list) {
                if (!p.test(item)) {
                    return list.subList(0, i);
                }
                i++;
            }
            return list;
        }
    */
    public static class PrimeNumbersCollector
            implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return () -> new HashMap<Boolean, List<Integer>>() {{
                put(true, new ArrayList<Integer>());
                put(false, new ArrayList<Integer>());
            }};
        }

        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
                acc.get(isPrime(acc.get(true),
                        candidate))
                        .add(candidate);
            };
        }

        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
            return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
                map1.get(true).addAll(map2.get(true));
                map1.get(false).addAll(map2.get(false));
                return map1;
            };
        }

        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            return i -> i;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
        }
    }
}
