package dataStructure;


public class LinkStack<T> {
    private Node first;
    private int N;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void push(T t) {


        Node oldFirst = first;
        first = new Node();
        first.item = t;
        first.next = oldFirst;
        N++;
    }

    public T pop() {
        T t = first.item;
        first = first.next;
        N--;
        return t;
    }

    private class Node {
        T item;
        Node next;
    }

}
