package sort;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaxPQ<T> implements Iterable<T> {
    private T[] pq;                    // store items at indices 1 to N
    private int N;                       // number of items on priority queue
    private Comparator<T> comparator;  // optional comparator

    public MaxPQ(int initCapacity) {
        pq = (T[]) new Object[initCapacity + 1];
        N = 0;
    }

    public MaxPQ() {
        this(1);
    }

    /**
     * 导入自定义的比较器
     *
     * @param initCapacity 堆大小
     * @param comparator   自定义的比较器,需要实现Comparator接口
     */
    public MaxPQ(int initCapacity, Comparator<T> comparator) {
        this.comparator = comparator;
        pq = (T[]) new Object[initCapacity + 1];
        N = 0;
    }

    public MaxPQ(Comparator<T> comparator) {
        this(1, comparator);
    }

    /**
     * 将一个数组转化成堆
     *
     * @param Ts
     */
    public MaxPQ(T[] Ts) {
        N = Ts.length;
        pq = (T[]) new Object[Ts.length + 1];
        for (int i = 0; i < N; i++)
            pq[i + 1] = Ts[i];
        for (int k = N / 2; k >= 1; k--)
            sink(k, N);
        assert isMaxHeap();
    }

    public static void main(String[] args) {
//        MaxPQ<String> pq = new MaxPQ<String>();
//        while (!StdIn.isEmpty()) {
//            String item = StdIn.readString();
//            if (!item.equals("-")) pq.insert(item);
//            else if (!pq.isEmpty()) StdOut.print(pq.delMax() + " ");
//        }
//        StdOut.println("(" + pq.size() + " left on pq)");
        Integer[] a = new Integer[10];
        a[0] = 14;
        a[1] = 1344;
        a[2] = 343414;
        a[3] = 134344;
        a[4] = 1344;
        a[5] = 1654;
        a[6] = 76;
        a[7] = 1478;
        a[8] = 1894;
        a[9] = 104;
        MaxPQ maxPQ = new MaxPQ();
        maxPQ.sort(a);
        System.out.println(a.toString());
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    /**
     * 得到堆中的最大元素
     * 因为pq[0]是不用的,所以直接反悔pq[1]
     *
     * @return
     */
    public T max() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    // helper function to double the size of the heap array
    private void resize(int capacity) {
        assert capacity > N;
        T[] temp = (T[]) new Object[capacity];
        for (int i = 1; i <= N; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    /**
     * 在堆中插入元素
     * 同时还有控制数组大小的功能
     *
     * @param x
     */
    public void insert(T x) {

        // double size of array if necessary
        if (N == pq.length - 1) resize(2 * pq.length);

        // add x, and percolate it up to maintain heap invariant
        pq[++N] = x;
        swim(N);
        assert isMaxHeap();
    }

    /**
     * 删除堆中的最大元素
     * 同时还有控制数组大小的功能
     *
     * @return
     */
    public T delMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        T max = pq[1];
        exch(pq, 1, N--);
        sink(1, N);
        pq[N + 1] = null;     // to avoid loiteing and help with garbage collection
        if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length / 2);
        assert isMaxHeap();
        return max;
    }

    /***************************************************************************
     * Helper functions to restore the heap invariant.
     ***************************************************************************/

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(pq, k, k / 2);
            k = k / 2;
        }
    }

    /**
     * 下沉方法
     *
     * @param k      需要下沉的元素
     * @param PQSize 在给定大小的子堆中下沉.在堆排序中,比如说第一次排序,最后一个元素是已经排好序的,PQSize=N-1
     */
    private void sink(int k, int PQSize) {
        while (2 * k <= PQSize) {
            int j = 2 * k;
            if (j < PQSize && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    /**
     * 比较大小
     *
     * @param i
     * @param j
     * @return
     */
    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<T>) pq[i]).compareTo(pq[j]) < 0;
        } else {
            //如果比较器不为空,则调用自定义的比较器的排序规则对数组进行大小的比较
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    /**
     * 交换堆中的两个元素
     *
     * @param i
     * @param j
     */
    private void exch(T[] a, int i, int j) {
        T swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // is pq[1..N] a max heap?
    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }

    // is subtree of pq[1..N] rooted at k a max heap?
    private boolean isMaxHeap(int k) {
        if (k > N) return true;
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left <= N && less(k, left)) return false;
        if (right <= N && less(k, right)) return false;
        return isMaxHeap(left) && isMaxHeap(right);
    }

    public Iterator<T> iterator() {
        return new HeapIterator();
    }

    /**
     * 堆排序的主要方法
     *
     * @param comparators 外界给的需要排序的数组,还不是堆
     */
    public void sort(T[] comparators) {
        //
        int N = comparators.length;
        //利用构造方法,将外界传进来的数组转化成堆
        //和书上相比少了一个for循环,因为该循环已存在于构造方法中
        MaxPQ a = new MaxPQ(comparators);
        while (N > 1) {
            //将最大的元素----第一个元素和最后一个元素调换
            exch(pq, 1, N--);
            //对第一个元素下沉
            sink(1, N);
        }
    }

    private class HeapIterator implements Iterator<T> {

        // create a new pq
        private MaxPQ<T> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no Ts move
        public HeapIterator() {
            if (comparator == null) copy = new MaxPQ<T>(size());
            else copy = new MaxPQ<T>(size(), comparator);
            for (int i = 1; i <= N; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();
        }
    }

}
