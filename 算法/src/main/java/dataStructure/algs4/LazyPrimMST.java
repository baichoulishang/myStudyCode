package dataStructure.algs4;


public class LazyPrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double weight;
    private Queue<Edge> mst;
    private boolean[] marked;
    private MinPQ<Edge> pq;

    public LazyPrimMST(EdgeWeightedGraph G) {
        mst = new Queue<Edge>();
        pq = new MinPQ<Edge>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                prim(G, v);
            }
        }


        assert check(G);
    }


    public void LazyPrimMST2(EdgeWeightedGraph G) {
        mst = new Queue<Edge>();
        pq = new MinPQ<Edge>();
        marked = new boolean[G.V()];
        visit(G, 0);

        while (!pq.isEmpty()) { // 对优先队列进行循环
            Edge edge = pq.delMin(); // 删除并返回优先队列的最小值,即权重最小的边
            int v = edge.either();
            int w = edge.other(v);
            // 如果两个顶点都被标记过,这条边无效,进入下一次循环
            if (marked[v] && marked[w]) {
                continue;
            }
            mst.enqueue(edge);// 否则,将这条边到最小生成树中
            // 从两个顶点中找到没标记过的那个,然后根据该顶点继续更新横切边的集合
            if (!marked[v]) {
                visit(G, v);
            }
            if (!marked[w]) {
                visit(G, w);
            }
        }
        assert check(G);
    }

    // 更新横切边的集合
    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;// 标记这个顶点已经被访问过了
        for (Edge e : G.adj(v)) {// 遍历与v相连的所有边
            // 判断另一个顶点是否已经被标记过了
            if (!marked[e.other(v)]) {
                pq.insert(e);// 如果没有的话,添加到优先队列中
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        LazyPrimMST mst = new LazyPrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }

    private void prim(EdgeWeightedGraph G, int s) {
        scan(G, s);
        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            assert marked[v] || marked[w];
            if (marked[v] && marked[w]) {
                continue;
            }
            mst.enqueue(e);
            weight += e.weight();
            if (!marked[v]) {
                scan(G, v);
            }
            if (!marked[w]) {
                scan(G, w);
            }
        }
    }

    private void scan(EdgeWeightedGraph G, int v) {
        assert !marked[v];
        marked[v] = true;
        for (Edge e : G.adj(v))
            if (!marked[e.other(v)]) pq.insert(e);
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
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
            for (Edge f : mst) {
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


