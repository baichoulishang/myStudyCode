package dataStructure.algs4;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public final class BinaryOut {

    private BufferedOutputStream out;
    private int buffer;
    private int n;


    public BinaryOut() {
        out = new BufferedOutputStream(System.out);
    }


    public BinaryOut(OutputStream os) {
        out = new BufferedOutputStream(os);
    }


    public BinaryOut(String filename) {
        try {
            OutputStream os = new FileOutputStream(filename);
            out = new BufferedOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public BinaryOut(Socket socket) {
        try {
            OutputStream os = socket.getOutputStream();
            out = new BufferedOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        String filename = args[0];
        BinaryOut out = new BinaryOut(filename);
        BinaryIn in = new BinaryIn();


        while (!in.isEmpty()) {
            char c = in.readChar();
            out.write(c);
        }
        out.flush();
    }

    private void writeBit(boolean x) {

        buffer <<= 1;
        if (x) buffer |= 1;


        n++;
        if (n == 8) clearBuffer();
    }

    private void writeByte(int x) {
        assert x >= 0 && x < 256;


        if (n == 0) {
            try {
                out.write(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }


        for (int i = 0; i < 8; i++) {
            boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    private void clearBuffer() {
        if (n == 0) return;
        if (n > 0) buffer <<= (8 - n);
        try {
            out.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        n = 0;
        buffer = 0;
    }

    public void flush() {
        clearBuffer();
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        flush();
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(boolean x) {
        writeBit(x);
    }

    public void write(byte x) {
        writeByte(x & 0xff);
    }

    public void write(int x) {
        writeByte((x >>> 24) & 0xff);
        writeByte((x >>> 16) & 0xff);
        writeByte((x >>> 8) & 0xff);
        writeByte((x >>> 0) & 0xff);
    }

    public void write(int x, int r) {
        if (r == 32) {
            write(x);
            return;
        }
        if (r < 1 || r > 32) throw new IllegalArgumentException("Illegal value for r = " + r);
        if (x >= (1 << r)) throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        for (int i = 0; i < r; i++) {
            boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    public void write(double x) {
        write(Double.doubleToRawLongBits(x));
    }

    public void write(long x) {
        writeByte((int) ((x >>> 56) & 0xff));
        writeByte((int) ((x >>> 48) & 0xff));
        writeByte((int) ((x >>> 40) & 0xff));
        writeByte((int) ((x >>> 32) & 0xff));
        writeByte((int) ((x >>> 24) & 0xff));
        writeByte((int) ((x >>> 16) & 0xff));
        writeByte((int) ((x >>> 8) & 0xff));
        writeByte((int) ((x >>> 0) & 0xff));
    }

    public void write(float x) {
        write(Float.floatToRawIntBits(x));
    }

    public void write(short x) {
        writeByte((x >>> 8) & 0xff);
        writeByte((x >>> 0) & 0xff);
    }

    public void write(char x) {
        if (x < 0 || x >= 256) throw new IllegalArgumentException("Illegal 8-bit char = " + x);
        writeByte(x);
    }

    public void write(char x, int r) {
        if (r == 8) {
            write(x);
            return;
        }
        if (r < 1 || r > 16) throw new IllegalArgumentException("Illegal value for r = " + r);
        if (x >= (1 << r)) throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        for (int i = 0; i < r; i++) {
            boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    public void write(String s) {
        for (int i = 0; i < s.length(); i++)
            write(s.charAt(i));
    }

    public void write(String s, int r) {
        for (int i = 0; i < s.length(); i++)
            write(s.charAt(i), r);
    }

}


