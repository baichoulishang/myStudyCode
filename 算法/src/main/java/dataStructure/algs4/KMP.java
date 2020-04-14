package dataStructure.algs4;


public class KMP {
    private final int R;
    private int[][] dfa;

    private char[] pattern;
    private String pat;


    public KMP(String pat) {
        this.R = 256;
        this.pat = pat;


        int m = pat.length();
        dfa = new int[R][m];
        dfa[pat.charAt(0)][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][x];
            dfa[pat.charAt(j)][j] = j + 1;
            x = dfa[pat.charAt(j)][x];
        }
    }


    public KMP(char[] pattern, int R) {
        this.R = R;
        this.pattern = new char[pattern.length];
        for (int j = 0; j < pattern.length; j++) {
            this.pattern[j] = pattern[j];
        }
        int m = pattern.length;
        dfa = new int[R][m];
        // 定义一个最基础的dfa
        dfa[pattern[0]][0] = 1;
        int X = 0;// 初始化重启状态
        for (int j = 1; j < m; j++) {
            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][X];// 把重启状态的dfa的值赋给j
            }
            dfa[pattern[j]][j] = j + 1;// 匹配成功,+1
            X = dfa[pattern[j]][X];// 更新重启状态
        }
    }

    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];
        char[] pattern = pat.toCharArray();
        char[] text = txt.toCharArray();

        KMP kmp1 = new KMP(pat);
        int offset1 = kmp1.search(txt);

        KMP kmp2 = new KMP(pattern, 256);
        int offset2 = kmp2.search(text);


        StdOut.println("text:    " + txt);

        StdOut.print("pattern: ");
        for (int i = 0; i < offset1; i++)
            StdOut.print(" ");
        StdOut.println(pat);

        StdOut.print("pattern: ");
        for (int i = 0; i < offset2; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }


    public static int searchOne(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();
        for (int i = 0; i <= n - m; i++) {
            int j;// 母字符串指针循环0到n-m
            for (j = 0; j < m; j++) {
                //子字符串循环0到m-1
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    break; // 只要有一个字符不同，跳出循环，从母字符串的下一个字符重新开始
                }
            }
            if (j == m) {
                return i;//说明子字符串所有字符都匹配
            }
        }
        return n;
    }

    public static int searchTwo(String pat, String txt) {
        int i, m = pat.length();// 匹配模式
        int j, n = txt.length();// 匹配文本
        for (i = 0, j = 0; i < n && j < m; i++) {// 每次循环都有两个判断条件! i < n 且 j < m
            if (txt.charAt(i) == pat.charAt(j)) {// i递增
                j++;// 匹配 j才增加,并且下一次循环匹配下个字符
            } else { // 不匹配
                i -= j;// i回退后 for循环再+1
                j = 0;// 模式指针回到开始
            }
        }
        if (j == m) {// 退出循环了,但是要判断一下退出的原因
            //暴力法一 i不会回退
            return i - m;
        } else {
            return n;
        }
    }

    public int search(String txt) {
        int m = pat.length();// 模式长度
        int n = txt.length();// 文本长度
        int i, j;// i:文本指针;j:模式指针
        for (i = 0, j = 0; i < n && j < m; i++) {
            // 由于dfa的定义是:对于每个字符c，在比较了c和pat.charAt(j)之后，dfa[c][j]表示的是应该和下个文本字符比较的模式字符的位置。
            // 所以,文本指针i自然递增
            // 模式指针j则根据dfa来计算下一个和文本匹配的模式索引.
            j = dfa[txt.charAt(i)][j];
        }
        if (j == m) {
            return i - m;
        }
        check("string");
        return n;

    }

    public <T> void check(T t) {

    }

    public int search(char[] text) {


        int m = pattern.length;
        int n = text.length;
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[text[i]][j];
        }
        if (j == m) return i - m;
        return n;
    }
}


