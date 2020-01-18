package lambdasinaction.chap5;

import lambdasinaction.chap4.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static lambdasinaction.chap4.Dish.menu;

public class Mapping {

    public static void main(String... args) {

        // map
        List<String> dishNames = menu.stream()
                .map(Dish::getName)
                .collect(toList());
        System.out.println(dishNames);

        // map
        List<String> words = Arrays.asList("Hello", "World");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());
        System.out.println(wordLengths);

        List<Stream<String>> collect = words.stream()
                .map(word -> word.split(","))
                .map(Arrays::stream)
                .distinct()
                .collect(toList());

        // flatMap
        words.stream()
                .flatMap((String line) -> Arrays.stream(line.split("")))
                .distinct()
                .forEach(System.out::println);

        // flatMap
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> numbers2 = Arrays.asList(6, 7, 8);
        List<Stream<int[]>> wrongList = numbers1.stream()
                // 第一个map中的Function的返回类型是Stream<int[]>,导致最后返回的List的类型也是List<Stream<int[]>>
                .map(i -> numbers2.stream().map(j -> new int[]{i, j}))// 返回的是Stream<int[]>类型的
                .collect(toList());
        List<int[]> rightList = numbers1.stream()
                // 第一个map中的Function的返回类型是Stream<int[]>
                // 使用flatMap,使Stream<int[]>中的每个int[]都生成一个单独的Stream
                .flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))// 返回的是Stream<int[]>类型的
                .collect(toList());


        List<int[]> pairs =
                numbers1.stream()
                        .flatMap(i -> numbers2.stream().map((Integer j) -> new int[]{i, j}))
                        .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                        .collect(toList());
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));
    }
}
