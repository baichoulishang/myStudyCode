//: innerclasses/DotNew.java
package innerclasses; /* Added by Eclipse.py */
// Creating an inner class directly using the .new syntax.

public class DotNew {
    public static void main(String[] args) {
        DotNew dn = new DotNew();
        Inner dni = dn.new Inner();
    }

    public class Inner {
    }
} ///:~
