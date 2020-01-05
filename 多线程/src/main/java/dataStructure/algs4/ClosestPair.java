package dataStructure.algs4;

import java.util.Arrays;


public class ClosestPair {


    private Point2D best1, best2;
    private double bestDistance = Double.POSITIVE_INFINITY;


    public ClosestPair(Point2D[] points) {
        if (points == null) throw new IllegalArgumentException("constructor argument is null");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("array element " + i + " is null");
        }

        int n = points.length;
        if (n <= 1) return;


        Point2D[] pointsByX = new Point2D[n];
        for (int i = 0; i < n; i++)
            pointsByX[i] = points[i];
        Arrays.sort(pointsByX, Point2D.X_ORDER);


        for (int i = 0; i < n - 1; i++) {
            if (pointsByX[i].equals(pointsByX[i + 1])) {
                bestDistance = 0.0;
                best1 = pointsByX[i];
                best2 = pointsByX[i + 1];
                return;
            }
        }


        Point2D[] pointsByY = new Point2D[n];
        for (int i = 0; i < n; i++)
            pointsByY[i] = pointsByX[i];


        Point2D[] aux = new Point2D[n];

        closest(pointsByX, pointsByY, aux, 0, n - 1);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    /**
     * 归并排序的底层归并算法
     *
     * @param a   需要归并的数组
     * @param aux 辅助数组.赋值原数组,不修改
     * @param lo  开始下标
     * @param mid 中间点
     * @param hi  结束下标
     * @return void
     * @Date 20:56 2019/11/3
     **/
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // 创建一个辅助数组,并且将已经左部分和右部分单独有毒的数组赋给它
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo, j = mid + 1;
        // 方法在归并时进行了4个条件判断；左半边用尽（取右半边的元素）、右半边用尽（取左半边的元素）、
        // 右半边的当前元素小于左半边的当前元素（取右半边的元素）以及右半边的当前元素大于等于左半边的当前元素（取左半边的元素）。
        // 辅助数组在被赋值之后就不再发生变化
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            points[i] = new Point2D(x, y);
        }
        ClosestPair closest = new ClosestPair(points);
        StdOut.println(closest.distance() + " from " + closest.either() + " to " + closest.other());
    }

    private double closest(Point2D[] pointsByX, Point2D[] pointsByY, Point2D[] aux, int lo, int hi) {
        if (hi <= lo) return Double.POSITIVE_INFINITY;

        int mid = lo + (hi - lo) / 2;
        Point2D median = pointsByX[mid];


        double delta1 = closest(pointsByX, pointsByY, aux, lo, mid);
        double delta2 = closest(pointsByX, pointsByY, aux, mid + 1, hi);
        double delta = Math.min(delta1, delta2);


        merge(pointsByY, aux, lo, mid, hi);


        int m = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(pointsByY[i].x() - median.x()) < delta)
                aux[m++] = pointsByY[i];
        }


        for (int i = 0; i < m; i++) {

            for (int j = i + 1; (j < m) && (aux[j].y() - aux[i].y() < delta); j++) {
                double distance = aux[i].distanceTo(aux[j]);
                if (distance < delta) {
                    delta = distance;
                    if (distance < bestDistance) {
                        bestDistance = delta;
                        best1 = aux[i];
                        best2 = aux[j];

                    }
                }
            }
        }
        return delta;
    }

    public Point2D either() {
        return best1;
    }

    public Point2D other() {
        return best2;
    }

    public double distance() {
        return bestDistance;
    }

}


