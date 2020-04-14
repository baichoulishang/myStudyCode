package dataStructure.algs4;


public class LongestRepeatedSubstring {


    private LongestRepeatedSubstring() {
    }


    public static String lrs(String text) {
        int n = text.length();
        // 先把text转化成有序后缀数组
        SuffixArray sa = new SuffixArray(text);
        String lrs = "";
        for (int i = 1; i < n; i++) {
            // select(i)和select(i-1)的最长公共前缀的长度（i在1到N-1之间）
            int length = sa.lcp(i);
            // 比较之前维护的长度
            if (length > lrs.length()) {
                lrs = text.substring(sa.index(i), sa.index(i) + length);
            }
        }
        return lrs;
    }


    public static void main(String[] args) {
        String text = StdIn.readAll().replaceAll("\\s+", " ");
        StdOut.println("'" + lrs(text) + "'");
    }
}


