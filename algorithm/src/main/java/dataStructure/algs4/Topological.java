package dataStructure.algs4;

public class Topological {
    private Iterable<Integer> order;
    private int[] rank;

    public Topological(Digraph G) {
        // 先根据传过来的有向图来生成另一种有向图数据结构,主要用来判断是否有环
        DirectedCycle finder = new DirectedCycle(G);
        if (!finder.hasCycle()) {// 判断是否有环
            DepthFirstOrder dfs = new DepthFirstOrder(G);// 再根据传过来的有向图生成逆后序
            order = dfs.reversePost();// 得到逆后序
            rank = new int[G.V()];
            int i = 0;
            for (int v : order) {
                rank[v] = i++;
            }
        }
    }

    public Topological(EdgeWeightedDigraph G) {
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);

        if (!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);

            order = dfs.reversePost();
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean hasOrder() {
        return order != null;
    }

    @Deprecated
    public boolean isDAG() {
        return hasOrder();
    }

    public int rank(int v) {
        validateVertex(v);
        if (hasOrder()) return rank[v];
        else return -1;
    }


    private void validateVertex(int v) {
        int V = rank.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }


}

