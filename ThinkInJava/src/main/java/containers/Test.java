//: containers/Test.java
package containers; /* Added by Eclipse.py */
// Framework for performing timed tests of containers.

import javax.management.relation.Role;
import java.lang.reflect.Field;

public abstract class Test<C> {
    String name;

    public Test(String name) {
        this.name = name;
    }

    // Override this method for different tests.
    // Returns actual number of repetitions of test.
    abstract int test(C container, TestParam tp);

    public static void main(String[] args) {
        Role role = new Role(null, null);

        Class<? extends Role> aClass = role.getClass();
        Field[] fields = aClass.getDeclaredFields();
        aClass.getMethods();
        aClass.getDeclaredMethods();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String simpleName = field.getType().getSimpleName();
            System.out.println(simpleName);

        }
    }
} ///:~
