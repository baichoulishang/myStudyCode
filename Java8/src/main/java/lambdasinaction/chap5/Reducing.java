package lambdasinaction.chap5;

import lambdasinaction.chap4.Dish;
import lambdasinaction.chap6.ToListCollector;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static lambdasinaction.chap4.Dish.menu;

public class Reducing {

    public static void main(String... args) {

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        int sum2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum2);

        int max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
        System.out.println(max);

        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);

        Map<String, List<Dish>> map = menu.stream().collect(Collectors.groupingBy(Dish::getName));
        Map<String, Dish> dishMap = menu.stream().collect(Collectors.toMap(Dish::getName, Function.identity()));
        int calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println("Number of calories:" + calories);
        List<Dish> collect = menu.stream().collect(new ToListCollector<Dish>());
        menu.stream().collect(
                ArrayList::new,
                List::add,
                List::addAll);
    }
}
