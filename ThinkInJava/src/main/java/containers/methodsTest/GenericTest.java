package containers.methodsTest;

import generics.Holder;
import reusing.BigCar;
import reusing.Car;

import java.util.List;

/**
 * @date 2020/3/27 20:29
 */
public class GenericTest {
    //Lev 1
    class Food {
    }

    //Lev 2
    class Fruit extends Food {
    }

    class Meat extends Food {
    }

    //Lev 3
    class Apple extends Fruit {
    }

    class Banana extends Fruit {
    }

    class Pork extends Meat {
    }

    class Beef extends Meat {
    }

    //Lev 4
    class RedApple extends Apple {
    }

    class GreenApple extends Apple {
    }

    class Plate<T> {
        private T item;

        public Plate(T t) {
            item = t;
        }

        public void set(T t) {
            item = t;
        }

        public T get() {
            return item;
        }
    }

    static <T extends Car> void wildSupertype(Holder<? super T> holder, T arg) {
        holder.set(arg);
        Object obj = holder.get();
    }

    public <T> List<T> fill(T... t) {
        return null;
    }

    public void sthHighLevel() {
        // wildSupertype(new Holder<Fruit>(), new Fruit());
        // 由于使用了下界通配符,所以Holder可以接受的类型是T以及T的子类
        // 如果我们使用了Fruit,那么说明Fruit必然是T的子类.由继承关系可得,Apple也必然是T的子类.
        // 虽然我们一直强调在使用泛型参数T时,T必然有个具体的类型.但是在这里,我们没法推断出T的具体类型,只能得出两点:
        // 1.编译器假设T已经有具体类型（虽然我们不知道具体类型）
        // 2.Fruit是该具体类型或者该具体类型的子类
        // wildSupertype(new Holder<Fruit>(), new Apple());
        // wildSupertype(new Holder<Fruit>(), new Food());  // error
        wildSupertype(new Holder<Car>(), new BigCar());  // error

    }


    public void main(String[] args) {


        // Plate<? super Fruit> p = new Plate<Fruit>(new Fruit());
        // //存入元素正常
        // p.set(new Fruit());
        // p.set(new Apple());
        // //读取出来的东西只能存放在Object类里。
        // Apple newFruit3 = p.get();    //Error
        // Fruit newFruit1 = p.get();    //Error
        // Object newFruit2 = p.get();


    }

}
