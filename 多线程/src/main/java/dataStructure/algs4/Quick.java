package dataStructure.algs4;


public class Quick {


    private Quick() {
    }


    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }


    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
        assert isSorted(a, lo, hi);
    }


    /**
     * 核心切分算法
     *
     * @param a  要排序的数组
     * @param lo 开始下标
     * @param hi 结束下标
     * @return int
     * @Date 20:00 2019/11/5 切分点的下标.初始阶段就是把a[lo]移动到该处
     **/
    private static int partition(Comparable[] a, int lo, int hi) {
        // 左扫描指针
        int i = lo;
        // 右扫描指针
        int j = hi + 1;
        // 作为切分点的元素
        Comparable v = a[lo];
        // 该算法会从小到大排序
        while (true) {
            // 一直从左往右扫描,直到找到比v大的元素
            // 此时less(a[++i], v)返回false,退出while循环
            while (less(a[++i], v)) {
                if (i == hi) break;
            }
            // 一直从右往左扫描,直到找到比v小的元素
            // 此时less(a[++i], v)返回false,退出while循环
            while (less(v, a[--j])) {
                if (j == lo) break;
            }
            if (i >= j) break;
            // 交换a[i]和a[j]的元素
            exch(a, i, j);
        }
        // 交换a[lo]和a[j]的元素.由于移动到最后,i>=j,所以我们和j交换
        exch(a, lo, j);
        return j;
    }


    public static Comparable select(Comparable[] a, int k) {
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("index is not between 0 and " + a.length + ": " + k);
        }
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int i = partition(a, lo, hi);
            if (i > k) hi = i - 1;
            else if (i < k) lo = i + 1;
            else return a[i];
        }
        return a[lo];
    }


    private static boolean less(Comparable v, Comparable w) {
        if (v == w) return false;
        return v.compareTo(w) < 0;
    }


    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }


    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }


    public static void main(String[] args) {
        Integer[] a = {45, 65, 675, 12, 645, 13, 46, 4, 564, 56, 4, 1, 11};
        StdRandom.shuffle(a);
        sort(a);
        System.out.println();
    }

}


