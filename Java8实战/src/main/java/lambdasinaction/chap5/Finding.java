package lambdasinaction.chap5;

import lambdasinaction.chap4.Dish;

import java.util.IntSummaryStatistics;
import java.util.Optional;

import static java.util.stream.Collectors.summarizingInt;
import static lambdasinaction.chap4.Dish.menu;

public class Finding {

    public static void main(String... args) {
        if (isVegetarianFriendlyMenu()) {
            System.out.println("Vegetarian friendly");
        }

        System.out.println(isHealthyMenu());
        System.out.println(isHealthyMenu2());

        Optional<Dish> dish = findVegetarianDish();
        dish.ifPresent(d -> System.out.println(d.getName()));
        IntSummaryStatistics collect = menu.stream().collect(summarizingInt(Dish::getCalories));
    }

    private static boolean isVegetarianFriendlyMenu() {
        return menu.stream().anyMatch(Dish::isVegetarian);
    }

    private static boolean isHealthyMenu() {
        return menu.stream().allMatch(d -> d.getCalories() < 1000);
    }

    private static boolean isHealthyMenu2() {
        return menu.stream().noneMatch(d -> d.getCalories() >= 1000);
    }

    private static Optional<Dish> findVegetarianDish() {
        return menu.stream().filter(Dish::isVegetarian).findAny();
    }

}
