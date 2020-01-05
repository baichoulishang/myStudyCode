package dataStructure;

public class Queue<E> {
    private Object[] data = null;
    private int maxSize;
    private int front;
    private int rear;


    public Queue() {
        this(10);
    }

    public Queue(int initialSize) {
        if (initialSize >= 0) {
            this.maxSize = initialSize;
            data = new Object[initialSize];
            front = rear = 0;
        } else {
            throw new RuntimeException("初始化大小不能小于0：" + initialSize);
        }
    }


    public boolean empty() {
        return rear == front ? true : false;
    }


    public boolean add(E e) {
        if (rear == maxSize) {
            throw new RuntimeException("队列已满，无法插入新的元素！");
        } else {
            data[rear++] = e;
            return true;
        }
    }


    public E peek() {
        if (empty()) {
            throw new RuntimeException("空队列异常！");
        } else {
            return (E) data[front];
        }
    }


    public E poll() {
        if (empty()) {
            throw new RuntimeException("空队列异常！");
        } else {
            E value = (E) data[front];
            data[front++] = null;
            return value;
        }
    }


    public int length() {
        return rear - front;
    }
}
