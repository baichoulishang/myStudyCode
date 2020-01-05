package dataStructure.algs4;


public class Merge {


    private Merge() {
    }


    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);


        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }


        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }


        assert isSorted(a, lo, hi);
    }


    /**
     * 采用了递归的方式对一个数组进行归并排序,而且只用到了一个辅助数组aux
     * 这种算法先排序最底层的最小规模的元素,再逐渐让整个数组有序
     *
     * @param a   需要排序的数组
     * @param aux 辅助数组
     * @param lo  开始下标
     * @param hi  结束下标
     * @return void
     * @author CYK
     * @Date 19:37 2019/11/3
     **/
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        // 取得中位数
        int mid = lo + (hi - lo) / 2;
        // 对左半部分进行排序
        sort(a, aux, lo, mid);
        // 对右半部分进行排序
        sort(a, aux, mid + 1, hi);
        // 归并左右两部分
        merge(a, aux, lo, mid, hi);
    }


    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
        assert isSorted(a);
    }


    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }


    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }


    private static void merge(Comparable[] a, int[] index, int[] aux, int lo, int mid, int hi) {


        for (int k = lo; k <= hi; k++) {
            aux[k] = index[k];
        }


        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) index[k] = aux[j++];
            else if (j > hi) index[k] = aux[i++];
            else if (less(a[aux[j]], a[aux[i]])) index[k] = aux[j++];
            else index[k] = aux[i++];
        }
    }


    public static int[] indexSort(Comparable[] a) {
        int n = a.length;
        int[] index = new int[n];
        for (int i = 0; i < n; i++)
            index[i] = i;

        int[] aux = new int[n];
        sort(a, index, aux, 0, n - 1);
        return index;
    }


    private static void sort(Comparable[] a, int[] index, int[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, index, aux, lo, mid);
        sort(a, index, aux, mid + 1, hi);
        merge(a, index, aux, lo, mid, hi);
    }


    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }


    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Merge.sort(a);
        show(a);
    }
}


