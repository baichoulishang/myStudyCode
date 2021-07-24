package dataStructure.algs4;

public class SymbolGraph {
    private ST<String, Integer> st;
    private String[] keys;
    private Graph graph;


    public SymbolGraph(String filename, String delimiter) {
        st = new ST<String, Integer>();
        In in = new In(filename);
        while (!in.isEmpty()) {
            String[] a = in.readLine().split(delimiter);// 读取并用分隔符分割
            for (int i = 0; i < a.length; i++) {
                if (!st.contains(a[i]))
                    st.put(a[i], st.size()); // 为a[i]这个符号绑定索引
            }
        }
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name; // 创建反向索引
        }
        graph = new Graph(st.size());
        in = new In(filename);// 再次读取一遍输入内容
        while (in.hasNextLine()) {
            String[] data = in.readLine().split(delimiter);
            int v = st.get(data[0]);
            for (int i = 1; i < data.length; i++) {
                int w = st.get(data[i]);// 得到该符号在st中的保存的值,也就是在keys中的索引
                graph.addEdge(v, w);// 添加边
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


