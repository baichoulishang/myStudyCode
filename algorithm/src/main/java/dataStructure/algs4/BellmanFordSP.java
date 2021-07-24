package dataStructure.algs4;

public class BellmanFordSP {
    private double[] distTo;               // 从起点到某个顶点的距离
    private DirectedEdge[] edgeTo;         // 从起点到某个顶点的最后一条边
    private boolean[] onQueue;             // 某个顶点是否在序列中
    private Queue<Integer> queue;          // 序列顶部就是正在被放松的顶点
    private int cost;                      // releax()被调用的次数
    private Iterable<DirectedEdge> cycle;  // edgeTo[]中是否含有负权重环

    /**
     * 通过对任意边进行放松，重复V轮
     *
     * @param G
     * @param s
     */
    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQueue = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        // Bellman-Ford algorithm
        queue = new Queue<Integer>();// 创建一个队列用来保存下一轮要放松的顶点
        queue.enqueue(s);// 先将顶点塞进去
        onQueue[s] = true;// 记录某个顶点是否存在于队列中
        //队列不为空，而且不含有负权重环。
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.dequeue();// 从队列中取出一个顶点.由于是队列,所以不具备[排序]的性质,可以看做任意顺序
            onQueue[v] = false;// 标记不在序列中
            relax(G, v);
        }
    }

    /**
     * 对顶点进行放松
     *
     * @param G
     * @param v
     */
    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            //放松的基本操作
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;

                if (!onQueue[w]) {// 如果w不在队列中，则把它加入队列
                    queue.enqueue(w);
                    onQueue[w] = true;// 标记已经在队列中
                }
            }
            //每调用V次relax()，第V+1次执行时就找一次负权重环。也就是说，每处理了V+1个顶点之后就找一次。
            if (cost++ % G.V() == 0) {
                findNegativeCycle();
                if (hasNegativeCycle()) return;  // found a negative cycle
            }
        }
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    /**
     * 找到负权重的环
     */
    private void findNegativeCycle() {
        int V = edgeTo.length;
        //在将所有边放松V轮之后当且仅当队列非空时有向图中才存在从起点可达的负权重环。
        // 如果是这样，edgeTo[ ]数组所表示的子图中必然含有这个负权重环。
        // 因此，要实现negativeCycle()，会根据edgeTo[ ]中的边构造一幅加权有向图并在该图中检测环。
        // 我们会使用并修改4.2节中的 Directedcycle类来在加权有向图中寻找环
        // 这种检查的成本分为以下几个部分。
        // ①、根据edgeTo[ ]生成一个新的加权有向图
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++)
            if (edgeTo[v] != null)
                spt.addEdge(edgeTo[v]);
        //②、把这个加权有向图传入寻找有向环的算法中。该算法的构造方法会自动寻找图中的负权重环。
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
        //③、如果有则返回负权重环，支持迭代；如果没有则返回null
        cycle = finder.cycle();
    }

    public double distTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    /**
     * 判断下标的准确性
     *
     * @param v
     */
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}
