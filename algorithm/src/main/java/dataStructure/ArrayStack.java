package dataStructure;


import java.util.Iterator;


public class ArrayStack<T> implements Iterable {
    private T[] item;
    private int N;

    public ArrayStack(int n) {
        item = (T[]) new Object[n];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }


    public T pop() {
        T t = item[--N];
        item[N] = null;

        if (N > 0 && N == item.length / 4) {
            resize(item.length / 2);
        }
        return t;
    }


    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = item[i];
        }
        item = temp;
    }

    public void push(T t) {

        if (N == item.length) {
            resize(2 * item.length);
        }
        item[N++] = t;
    }


    @Override
    public Iterator iterator() {
        return new ReverseArrayIterator();
    }


    private class ReverseArrayIterator implements Iterator<T> {
        private int i = N;


        @Override
        public boolean hasNext() {
            return i > 0;
        }


        @Override
        public T next() {
            return item[--i];
        }

        @Override
        public void remove() {

        }
    }

}
