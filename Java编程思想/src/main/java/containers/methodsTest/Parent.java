package containers.methodsTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2020/2/22 20:50
 */
public class Parent {
    public int getNum() {

        List<? super Grandson> list = new ArrayList<Child>();

        return 1;
    }


}
