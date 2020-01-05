//: interfaces/AdaptedRandomDoubles.java
package interfaces; /* Added by Eclipse.py */
// Creating an adapter with inheritance.

import java.nio.CharBuffer;
import java.util.Scanner;

public class AdaptedRandomDoubles extends RandomDoubles
        implements Readable {
    private int count;

    public AdaptedRandomDoubles(int count) {
        this.count = count;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(new AdaptedRandomDoubles(7));
        while (s.hasNextDouble())
            System.out.print(s.nextDouble() + " ");
    }

    public int read(CharBuffer cb) {
        if (count-- == 0)
            return -1;
        String result = next() + " ";
        cb.append(result);
        return result.length();
    }
} /* Output:
0.7271157860730044 0.5309454508634242 0.16020656493302599 0.18847866977771732 0.5166020801268457 0.2678662084200585 0.2613610344283964
*///:~
