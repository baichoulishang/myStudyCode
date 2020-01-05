package dataStructure.algs4;

public class SymbolGraph {
    private ST<String, Integer> st;
    private String[] keys;
    private Graph graph;


    /**
     * 生成符号表
     *
     * @param filename
     * @param delimiter 分隔符.由于输入的文件内容是一行一行输入的,而且每一行中,第一个元素时顶点,其他的都是它的边,所以用分隔符分隔开
     */
    public SymbolGraph(String filename, String delimiter) {
        st = new ST<String, Integer>();

        In in = new In(filename);

        while (!in.isEmpty()) {
            // 读取并用分隔符分割
            String[] a = in.readLine().split(delimiter);
            for (int i = 0; i < a.length; i++) {
                // 判断是否已经加入到st中
                if (!st.contains(a[i]))
                    // 为a[i]这个符号绑定索引
                    st.put(a[i], st.size());
            }
        }


        keys = new String[st.size()];
        for (String name : st.keys()) {
            // 创建反向索引
            keys[st.get(name)] = name;
        }

        graph = new Graph(st.size());
        in = new In(filename);

        while (in.hasNextLine()) {
            // 再次读取一遍输入内容
            String[] a = in.readLine().split(delimiter);
            // 得到该符号在st中的保存的值,也就是在keys中的索引
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                // 遍历顶点
                int w = st.get(a[i]);
                // 添加边
                graph.addEdge(v, w);
            }
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        String delimiter = args[1];
        SymbolGraph sg = new SymbolGraph(filename, delimiter);
        Graph graph = sg.graph();
        while (StdIn.hasNextLine()) {
            String source = StdIn.readLine();
            if (sg.contains(source)) {
                int s = sg.index(source);
                for (int v : graph.adj(s)) {
                    StdOut.println("   " + sg.name(v));
                }
            } else {
                StdOut.println("input not contain '" + source + "'");
            }
        }
    }

    public boolean contains(String s) {
        return st.contains(s);
    }

    @Deprecated
    public int index(String s) {
        return st.get(s);
    }

    public int indexOf(String s) {
        return st.get(s);
    }

    @Deprecated
    public String name(int v) {
        validateVertex(v);
        return keys[v];
    }

    public String nameOf(int v) {
        validateVertex(v);
        return keys[v];
    }

    @Deprecated
    public Graph G() {
        return graph;
    }

    public Graph graph() {
        return graph;
    }

    private void validateVertex(int v) {
        int V = graph.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }
}


