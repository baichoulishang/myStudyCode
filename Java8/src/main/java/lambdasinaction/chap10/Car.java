package lambdasinaction.chap10;

import java.util.*;

public class Car {

    private Optional<Insurance> insurance;

    public static void check(List<? super BigCar> list) {
        list.add(new BigCar());
        list.add(new BigRedCar());
        // 报错了
        // list.add(new Car());

        /*测试一下*/
        List<? extends BigCar> list2 = new ArrayList<>();
        // list2.add(new BigRedCar());

    }

    public static void main(String[] args) {
        check(Collections.singletonList(new Car()));
        check(Collections.singletonList(new BigCar()));
        check(Collections.singletonList(new BigRedCar()));
    }

    /**
     * 测试往集合里面塞进去元素
     *
     * @param list 集合
     */
    public static void check2(List<? extends BigCar> list) {
        // list.add(new BigCar());
        // list.add(new BigRedCar());
    }

    public Optional<Insurance> getInsurance() {
        return insurance;
    }

    public <T extends BigCar> void study(T t) {

    }


}
