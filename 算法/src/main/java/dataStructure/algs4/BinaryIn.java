package dataStructure.algs4;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.NoSuchElementException;


public final class BinaryIn {
    private static final int EOF = -1;

    private BufferedInputStream in;
    private int buffer;
    private int n;


    public BinaryIn() {
        in = new BufferedInputStream(System.in);
        fillBuffer();
    }


    public BinaryIn(InputStream is) {
        in = new BufferedInputStream(is);
        fillBuffer();
    }


    public BinaryIn(Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            in = new BufferedInputStream(is);
            fillBuffer();
        } catch (IOException ioe) {
            System.err.println("Could not open " + socket);
        }
    }


    public BinaryIn(URL url) {
        try {
            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            in = new BufferedInputStream(is);
            fillBuffer();
        } catch (IOException ioe) {
            System.err.println("Could not open " + url);
        }
    }


    public BinaryIn(String name) {

        try {

            File file = new File(name);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                in = new BufferedInputStream(fis);
                fillBuffer();
                return;
            }


            URL url = getClass().getResource(name);


            if (url == null) {
                url = new URL(name);
            }

            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            in = new BufferedInputStream(is);
            fillBuffer();
        } catch (IOException ioe) {
            System.err.println("Could not open " + name);
        }
    }

    public static void main(String[] args) {
        BinaryIn in = new BinaryIn(args[0]);
        BinaryOut out = new BinaryOut(args[1]);


        while (!in.isEmpty()) {
            char c = in.readChar();
            out.write(c);
        }
        out.flush();
    }

    private void fillBuffer() {
        try {
            buffer = in.read();
            n = 8;
        } catch (IOException e) {
            System.err.println("EOF");
            buffer = EOF;
            n = -1;
        }
    }

    public boolean exists() {
        return in != null;
    }

    public boolean isEmpty() {
        return buffer == EOF;
    }

    public boolean readBoolean() {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");
        n--;
        boolean bit = ((buffer >> n) & 1) == 1;
        if (n == 0) fillBuffer();
        return bit;
    }

    public char readChar() {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");


        if (n == 8) {
            int x = buffer;
            fillBuffer();
            return (char) (x & 0xff);
        }


        int x = buffer;
        x <<= (8 - n);
        int oldN = n;
        fillBuffer();
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");
        n = oldN;
        x |= (buffer >>> n);
        return (char) (x & 0xff);


    }

    public char readChar(int r) {
        if (r < 1 || r > 16) throw new IllegalArgumentException("Illegal value of r = " + r);


        if (r == 8) return readChar();

        char x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) x |= 1;
        }
        return x;
    }

    public String readString() {
        if (isEmpty()) throw new NoSuchElementException("Reading from empty input stream");

        StringBuilder sb = new StringBuilder();
        while (!isEmpty()) {
            char c = readChar();
            sb.append(c);
        }
        return sb.toString();
    }

    public short readShort() {
        short x = 0;
        for (int i = 0; i < 2; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    public int readInt() {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    public int readInt(int r) {
        if (r < 1 || r > 32) throw new IllegalArgumentException("Illegal value of r = " + r);


        if (r == 32) return readInt();

        int x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) x |= 1;
        }
        return x;
    }

    public long readLong() {
        long x = 0;
        for (int i = 0; i < 8; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public byte readByte() {
        char c = readChar();
        return (byte) (c & 0xff);
    }
}


