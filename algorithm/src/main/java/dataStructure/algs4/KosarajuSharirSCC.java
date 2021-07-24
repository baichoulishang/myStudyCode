package dataStructure.algs4;

public class KosarajuSharirSCC {
    private boolean[] marked;
    private int[] id;
    private int count;

    public KosarajuSharirSCC(Digraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        // G.reverse()，就是G的逆序
        // dfs.reversePost() G的逆序的逆后序
        // 设我们生成的逆后序是a b c d v w x y z
        // 逆后序的意义：假如我们使用dfs()这个递归方法来生成逆后序，那么在生成的逆后序中，若v排在前面，w排在后面，dfs(w)必然在dfs(v)【结束之前】结束。
        // 换言之，dfs(a)是最后一个结束的。
        // 但是这个意义不能证明dfs(w)是在dfs(v)调用之前还是调用之后，这个还是要注意一下的。详细情况可以参考p.374页的图4.2.10
        DepthFirstOrder dfs = new DepthFirstOrder(G.reverse());
        for (int v : dfs.reversePost()) {
            if (!marked[v]) {
                dfs(G, v);
                count++;// 同一个连通分量的顶点一定会被标识同一个count
            }
        }

        assert check(G);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        KosarajuSharirSCC scc = new KosarajuSharirSCC(G);


        int m = scc.count();
        StdOut.println(m + " strong components");


        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[scc.id(v)].enqueue(v);
        }


        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }

    }

    private void dfs(Digraph G, int v) {
        marked[v] = true; //已经访问过的点都打个标记
        id[v] = count; //标记v属于哪个连通分量
        for (int w : G.adj(v)) {
            if (!marked[w]) dfs(G, w);
        }
    }

    public int count() {
        return count;
    }

    public boolean stronglyConnected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    private boolean check(Digraph G) {
        TransitiveClosure tc = new TransitiveClosure(G);
        for (int v = 0; v < G.V(); v++) {
            for (int w = 0; w < G.V(); w++) {
                if (stronglyConnected(v, w) != (tc.reachable(v, w) && tc.reachable(w, v)))
                    return false;
            }
        }
        return true;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}
