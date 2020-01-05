package dataStructure.algs4;

import java.io.File;


public class FileIndex {


    private FileIndex() {
    }

    public static void main(String[] args) {


        ST<String, SET<File>> st = new ST<String, SET<File>>();


        StdOut.println("Indexing files");
        for (String filename : args) {
            StdOut.println("  " + filename);
            File file = new File(filename);
            In in = new In(file);
            while (!in.isEmpty()) {
                //读取一个单词
                String word = in.readString();
                //如果st中没有这个单词，则创建一个键值对，值是SET对象
                if (!st.contains(word)) st.put(word, new SET<File>());
                //把这个文件放入这个set中
                SET<File> set = st.get(word);
                set.add(file);
            }
        }


        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            if (st.contains(query)) {
                SET<File> set = st.get(query);
                for (File file : set) {
                    StdOut.println("  " + file.getName());
                }
            }
        }

    }

}


