package dataStructure.algs4;

public class DirectedCycle {
    private boolean[] marked;
    private int[] edgeTo;
    private boolean[] onStack;
    private Stack<Integer> cycle;

    public DirectedCycle(Digraph G) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];

        for (int v = 0; v < G.V(); v++)
            if (!marked[v] && cycle == null) dfs(G, v);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        DirectedCycle finder = new DirectedCycle(G);
        if (finder.hasCycle()) {
            StdOut.print("Directed cycle: ");
            for (int v : finder.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        } else {
            StdOut.println("No directed cycle");
        }
        StdOut.println();
    }

    private void dfs(Digraph G, int v) {
        // 已经遍历过了,存进栈中
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v)) {
            // 如果cycle不为空,说明已经检测到了有向环,退出
            if (cycle != null) return;
                // 没有遍历过
            else if (!marked[w]) {
                // 标记路径
                edgeTo[w] = v;
                // 以w为起点继续深度搜索
                dfs(G, w);
                // 标记过了,而且w已经存进了栈中
            } else if (onStack[w]) {
                cycle = new Stack<Integer>();
                // 比如,3 → 4,4 → 5,5 → 3,那这个for循环会将3,4放进cycle中
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                // 存入5
                cycle.push(w);
                // 存入3
                cycle.push(v);
                assert check();
            }
        }
        // 从栈中取出v,因为已经不需要再遍历它了
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    private boolean check() {

        if (hasCycle()) {

            int first = -1, last = -1;
            for (int v : cycle()) {
                if (first == -1) first = v;
                last = v;
            }
            if (first != last) {
                System.err.printf("cycle begins with %d and ends with %d\n", first, last);
                return false;
            }
        }


        return true;
    }

}

