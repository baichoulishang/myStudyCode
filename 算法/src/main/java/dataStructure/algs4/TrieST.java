package dataStructure.algs4;


public class TrieST<Value> {
    private static final int R = 256;// 基于字母表
    private Node root;// 节点
    private int n;// 树中所有的val

    private static class Node {
        private Object val;// 值
        private Node[] next = new Node[R];// 节点关联的链接数组
    }

    public TrieST() {
    }

    public static void main(String[] args) {


        TrieST<Integer> st = new TrieST<Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }


        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }

        StdOut.println("longestPrefixOf(\"shellsort\"):");
        StdOut.println(st.longestPrefixOf("shellsort"));
        StdOut.println();

        StdOut.println("longestPrefixOf(\"quicksort\"):");
        StdOut.println(st.longestPrefixOf("quicksort"));
        StdOut.println();

        StdOut.println("keysWithPrefix(\"shor\"):");
        for (String s : st.keysWithPrefix("shor"))
            StdOut.println(s);
        StdOut.println();

        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l."))
            StdOut.println(s);
    }

    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);// 从根节点开始查询
        if (x == null) return null;
        return (Value) x.val;
    }


    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * 从某一个节点开始查询key
     *
     * @param x   开始查询的节点
     * @param key 要查找的key
     * @param d   查找深度.比较查找深度和key的长度,判断何时停下查找
     * @return 查找到的节点
     */
    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {// 如果查找深度==键长,说明已经找到了对应的节点
            return x;
        }
        char c = key.charAt(d);// 下一个要查找的字符
        // 从x节点的链接数组中挑选c链接指向的节点,并从该节点开始查找key
        // 同时增加查找深度
        return get(x.next[c], key, d + 1);
    }


    /**
     * 存入某个键值
     *
     * @param key 键
     * @param val 值
     */
    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
        } else {
            root = put(root, key, val, 0);// 根节点
        }
    }

    /**
     * 存入某个键值
     *
     * @param x   从该节点开始往下递归,直到深度d合适
     * @param key 键
     * @param val 值
     * @param d   递归到节点x时的深度
     * @return x节点的链表数组中, 用来存放key的一个节点
     */
    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (x.val == null) {// 如果当前节点的val为空,那么增加计数器
                n++;
            }
            x.val = val;// 设置值
            return x;
        }
        char c = key.charAt(d);// 得到下一个要查找的字符
        // 从x的链表数组中使用c节点来存放key和val,返回的是存放完成的节点
        // 同时增加递归深度
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }


    public int size() {
        return n;
    }


    public boolean isEmpty() {
        return size() == 0;
    }


    public Iterable<String> keys() {
        return keysWithPrefix("");
    }


    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    /**
     * 处理(遍历)节点x
     *
     * @param x       当前要处理的节点
     * @param prefix  之前的节点遍历完之后,拼接出来的字符串
     * @param results 用这个队列来保存所有遍历出来的字符串
     */
    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.val != null) {
            results.enqueue(prefix.toString());// 在队列中添加已经找到的key
        }
        for (char c = 0; c < R; c++) {// 遍历节点x的链接数组的所有元素
            prefix.append(c);// 先添加上这个字符
            collect(x.next[c], prefix, results);// 以节点x.next[c]为基础继续遍历
            // 删除掉最后一个字符,其实删除的就是c!!
            // 如果x.next[c]是null,那么删除的是c,反正当前节点是null,c原本是不该添加的;
            // 否则,根据递归的特点来推算一下,删除的还是c
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }


    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new Queue<String>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.val != null)
            results.enqueue(prefix.toString());
        if (d == pattern.length())
            return;
        char c = pattern.charAt(d);
        if (c == '.') {
            for (char ch = 0; ch < R; ch++) {
                prefix.append(ch);
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        } else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }


    public String longestPrefixOf(String query) {
        if (query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }


    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c], query, d + 1, length);
    }


    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.val != null) n--;
            x.val = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }


        if (x.val != null) return x;
        for (int c = 0; c < R; c++)
            if (x.next[c] != null)
                return x;
        return null;
    }


}


