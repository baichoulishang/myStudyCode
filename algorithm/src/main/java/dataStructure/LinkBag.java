package dataStructure;

import org.apache.poi.ss.formula.functions.T;

import java.util.Iterator;


public class LinkBag implements Iterable {
    private Node first;

    @Override
    public Iterator iterator() {
        return new bagIterator();
    }

    public void push(T t) {


        Node oldFirst = first;
        first = new Node();
        first.item = t;
        first.next = oldFirst;
    }

    private class Node {
        T item;
        Node next;
    }

    private class bagIterator implements Iterator {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Object next() {

            T t = current.item;

            current = current.next;
            return t;
        }

        @Override
        public void remove() {

        }
    }


}
