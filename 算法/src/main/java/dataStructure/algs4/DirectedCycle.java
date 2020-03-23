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
        onStack[v] = true;// 将v存入到栈中
        marked[v] = true;// 标记v已经遍历过了
        for (int w : G.adj(v)) {
            if (cycle != null) return;// 如果cycle不为空,说明已经检测到了有向环,退出
            else if (!marked[w]) {// 没有遍历过
                edgeTo[w] = v;// 标记从v到w的边
                dfs(G, w);// 以w为起点继续深度搜索
            } else if (onStack[w]) {// w已经存在栈中.进入这个分支,说明已经检测到了有向环!
                // 将这个环的信息保存下来
                cycle = new Stack<>();
                // 比如,3 → 4,4 → 5,5 → 3,那这个for循环会将3,4放进cycle中
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);// 存入5
                cycle.push(v); // 存入3
                assert check();
            }
        }
        // 从栈中删除v,因为已经执行完了for (int w : G.adj(v)) ，已经遍历了v所有的顶点
        // 如果不移除的话,后续可能会有边指向v,并对v顶点进行判断
        // 1、判断【!marked[w]】，返回false
        // 2、判断【onStack[w]】，返回true，从而判定存在有向环。这个结论是错的。
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

