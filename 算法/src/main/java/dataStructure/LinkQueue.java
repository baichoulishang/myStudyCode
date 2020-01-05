package dataStructure;

import java.util.NoSuchElementException;

public class LinkQueue<T> {
    private Node<T> first;
    private Node<T> last;
    private int N;

    public LinkQueue() {
        first = null;
        last = null;
        N = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("LinkQueue underflow");
        }
        return first.item;
    }

    public void enqueue(T item) {
        Node<T> oldlast = last;
        last = new Node<T>();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        N++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("LinkQueue underflow");
        }
        T item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) last = null;
        System.out.println(item);
        return item;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
    }
}
