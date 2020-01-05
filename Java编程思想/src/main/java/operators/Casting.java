//: operators/Casting.java
package operators; /* Added by Eclipse.py */

public class Casting {
    public static void main(String[] args) {
        int i = 200;
        long lng = i;
        lng = i; // "Widening," so cast not really required
        long lng2 = 200;
        lng2 = 200;
        // A "narrowing conversion":
        i = (int) lng2; // Cast required
    }
} ///:~
