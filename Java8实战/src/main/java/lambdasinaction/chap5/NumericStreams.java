package lambdasinaction.chap5;

import lambdasinaction.chap4.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static lambdasinaction.chap4.Dish.menu;

public class NumericStreams {

    public static void main(String... args) {

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

        Arrays.stream(numbers.toArray()).forEach(System.out::println);
        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println("Number of calories:" + calories);


        // max and OptionalInt
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        int max;
        if (maxCalories.isPresent()) {
            max = maxCalories.getAsInt();
        } else {
            // we can choose a default value
            max = 1;
        }
        System.out.println(max);

        // numeric ranges
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);

        System.out.println(evenNumbers.count());


        int firstLetter = 3;
        Stream<int[]> ggs = IntStream.rangeClosed(1, 100)
                .filter(b -> Math.sqrt(firstLetter * firstLetter + b * b) % 1 == 0)
                .boxed()
                .map(b -> new int[]{firstLetter, b, (int) Math.sqrt(firstLetter * firstLetter + b * b)});


        int original = 3;
        Stream<int[]> stream = IntStream.rangeClosed(original, 100)
                .filter(b -> Math.sqrt(original * original + b * b) % 1 == 0).boxed()
                .map(b -> new int[]{original, b, (int) Math.sqrt(original * original + b * b)});

        Stream<Stream<int[]>> streamStream = IntStream.rangeClosed(1, 100)
                .boxed()
                .map(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
                        .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));

        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
                        .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));

        pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

    }

    public static boolean isPerfectSquare(int n) {
        return Math.sqrt(n) % 1 == 0;
    }

}
