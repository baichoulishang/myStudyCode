package dataStructure.algs4;

public class DirectedDFS {
    private boolean[] marked;
    private int count;

    // Digraph是已经处理好的图的数据结构,适用符号表
    public DirectedDFS(Digraph G, int s) {
        // 初始化标记数组
        marked = new boolean[G.V()];
        validateVertex(s);
        // 调用深度优先搜索
        dfs(G, s);
    }

    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        validateVertices(sources);
        for (int v : sources) {
            if (!marked[v]) dfs(G, v);
        }
    }

    public static void main(String[] args) {


        In in = new In(args[0]);
        Digraph G = new Digraph(in);


        Bag<Integer> sources = new Bag<Integer>();
        for (int i = 1; i < args.length; i++) {
            int s = Integer.parseInt(args[i]);
            sources.add(s);
        }


        DirectedDFS dfs = new DirectedDFS(G, sources);


        for (int v = 0; v < G.V(); v++) {
            if (dfs.marked(v)) StdOut.print(v + " ");
        }
        StdOut.println();
    }

    private void dfs(Digraph G, int v) {
        count++;
        // 标记已经经过了
        marked[v] = true;
        // 遍历所有的出边
        for (int w : G.adj(v)) {
            // 如果没有被标记过,那么继续递归遍历
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    public int count() {
        return count;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
            }
        }
    }

}


