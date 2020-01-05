package sort;

import java.util.Scanner;

public class Example {
    /**
     * 排序算法类模板
     */
    public static void sort(Comparable[] a) {
        /*
         *
         */
    }

    /**
     * 比较两个元素的大小
     *
     * @param:
     * @return:
     * @date: 2019/4/7 17:03
     */
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    /**
     * 交换两个元素的位置
     *
     * @param a
     * @param i
     * @param j
     */
    private static void exch(Comparable[] a, int i, int j) {

        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /**
     * 展示所有元素
     *
     * @param:
     * @return:
     * @date: 2019/4/7 17:03
     */
    private static void show(Comparable[] a) {
        //在单行打印数组
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    /**
     * @param: [a]
     * @return: boolean
     * @date: 2019/4/7 17:05
     */
    public static boolean isSorted(Comparable[] a) {
        //测试数组是否有序
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //从标准输入读取字符串，将它们排序并输出
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        Comparable[] a = new Comparable[str.length()];

        for (int i = 0; i < str.length(); i++) {
            a[i] = str.charAt(i);
        }
        sort(a);
        assert isSorted(a);
        show(a);
    }
}