package dataStructure.algs4;

public class PrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private Edge[] edgeTo;// 树到某个顶点的边
    private double[] distTo;// 树到某个顶点的距离
    private boolean[] marked;// 判断某个顶点是否已经在树中
    private IndexMinPQ<Double> pq;// 索引优先队列!!其实我感觉这里使用优先队列还是索引优先队列都可以

    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];// 到某个顶点的边
        distTo = new double[G.V()];// 到某个顶点的边的权重
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;// 一开始将所有的边的权重都设定为无限.标记过之后,权重才有意义
        }
        distTo[0] = 0.0;
        pq.insert(0, distTo[0]);// 将顶点0的数据加入索引优先队列
        while (!pq.isEmpty()) {
            int s = pq.delMin();// 从优先队列中取出最小的顶点
            visit(G, s);// 处理这个顶点
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        PrimMST mst = new PrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;// 打上标记,已经遍历过
        for (Edge e : G.adj(v)) {// 遍历该顶点的所有相连的边
            int w = e.other(v);// 这条边的另一个顶点
            if (marked[w]) continue;// 如果已经在树中,则跳过
            if (e.weight() < distTo[w]) {// 如果从顶点v到那个顶点的距离小于目前已知的最小值
                distTo[w] = e.weight();// 更新到那个顶点的数据
                edgeTo[w] = e;
                if (pq.contains(w)) {
                    pq.decreaseKey(w, distTo[w]);// 如果优先队列中已经有那个点,则更新
                } else {
                    pq.insert(w, distTo[w]);// 否则,插入那个顶点的数据
                }
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<Edge>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }

    private boolean check(EdgeWeightedGraph G) {


        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight();
        }
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
        }


        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }


        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }


        for (Edge e : edges()) {


            uf = new UF(G.V());
            for (Edge f : edges()) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }


            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (!uf.connected(x, y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }


}


