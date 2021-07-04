package containers.methodsTest;

import java.lang.reflect.Method;

/**
 * @date 2020/2/22 20:50
 */
public class Child extends Parent {
    public int checkThat() {
        return 3;
    }

    public static void main(String[] args) {
        Child child = new Child();
        Class<? extends Child> childClass = child.getClass();
        Method[] methods = childClass.getMethods();
        Method[] methods2 = childClass.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            System.out.println(method.getName());
        }
        System.out.println("***************************分割线***************************");
        for (int i = 0; i < methods2.length; i++) {
            Method method = methods[i];
            System.out.println(method.getName());
        }
    }
}
