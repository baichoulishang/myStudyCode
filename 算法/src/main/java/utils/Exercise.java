package utils;

/**
 * Created by Megustas on 2017/3/20.
 */


public class Exercise {
    public static int distance(int[] a, int[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array dimensions disagree");
        }
        int[] aIndex = new int[a.length];
        int[] bIndex = new int[b.length];
        //用数组aIndex存储数组a的索引值,可以看做（i，a[i]）,（i,b[i]）
        for (int i = 0; i < a.length; i++) {
            aIndex[a[i]] = i;
        }
        System.out.println(arrToString(aIndex));
        //bIndex数组引用A数组的索引，即bIndex数组存储}b数组元素在a数组中的对应位置
        //如果a和b的顺序相同,那么bIndex的内容应该是0123456
        for (int i = 0; i < b.length; i++) {
            bIndex[i] = aIndex[b[i]];
        }
        System.out.println(arrToString(bIndex));
        return sortcount(bIndex);
    }

    public static String arrToString(int[] ts) {
        StringBuffer str5 = new StringBuffer();
        for (int s : ts) {
            str5.append(s);
        }
        return String.valueOf(str5);
    }

    public static int sortcount(int[] a) {
        int length = a.length;
        int counter = 0;
        //插入排序的交换次数就是倒置数
        for (int i = 1; i < length; i++) {
            for (int j = i; j > 0 && a[j] < a[j - 1]; j--) {
                int temp = a[j];
                a[j] = a[j - 1];
                a[j - 1] = temp;
                counter++;
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        int[] a = {0, 3, 1, 6, 2, 5, 4};
        int[] b = {0, 3, 1, 6, 2, 5, 4};
//        int[] a = {0,3,1,6,2,5,4};
//        int[] b = {1,0,3,6,4,2,5};
//        for (int i = 0;i < a.length;i++){
//            System.out.println(a[i] + " " +b[i]);
//        }
        System.out.println("Kendall tau :" + Exercise.distance(a, b));
    }

}
