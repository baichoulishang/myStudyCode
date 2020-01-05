package dataStructure.algs4;


public class LookupIndex {


    private LookupIndex() {
    }

    public static void main(String[] args) {
        String filename = args[0];
        //分隔符
        String separator = args[1];
        In in = new In(filename);

        //键对应着值
        ST<String, Queue<String>> st = new ST<String, Queue<String>>();
        //值对应着键
        ST<String, Queue<String>> ts = new ST<String, Queue<String>>();

        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(separator);
            String key = fields[0];
            for (int i = 1; i < fields.length; i++) {
                String val = fields[i];
                //如果st不包含键，则新建一行记录
                if (!st.contains(key)) st.put(key, new Queue<String>());
                //反过来把val当成键存进ts
                if (!ts.contains(val)) ts.put(val, new Queue<String>());
                //把val加入到key对应的队列中
                st.get(key).enqueue(val);
                ts.get(val).enqueue(key);
            }
        }

        StdOut.println("Done indexing");


        while (!StdIn.isEmpty()) {
            String query = StdIn.readLine();
            //依次输出所有的“值”
            if (st.contains(query))
                for (String vals : st.get(query))
                    StdOut.println("  " + vals);
            if (ts.contains(query))
                for (String keys : ts.get(query))
                    StdOut.println("  " + keys);
        }

    }

}


