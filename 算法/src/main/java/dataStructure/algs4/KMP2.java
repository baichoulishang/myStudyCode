package dataStructure.algs4;

/**
 * User: CYK
 * Date: 2020/4/3 0003
 * Time: 14:22
 * Description: No Description
 */
public class KMP2 {
    public static void main(String[] args) {
        new KMP2("ABCABCCABCABABCCBACBACBABAC");
        /*************012345678901234567890123456789*/
    }

    private int[][] dp;
    private String pat;

    public KMP2(String pat) {
        this.pat = pat;
        int M = pat.length();
        // dp[状态][字符] = 下个状态
        dp = new int[M][256];
        // base case
        dp[0][pat.charAt(0)] = 1;
        // 影子状态 X 初始为 0
        int X = 0;
        // 构建状态转移图（稍改的更紧凑了）
        for (int j = 1; j < M; j++) {
            for (int c = 0; c < 256; c++) {
                // 将影子的状态传递给j.由于j和X具有相同的最长前缀,所以可以看成回退处理
                // 回退处理:在暴力算法中,如果匹配失败,整个模式字符串都要回退.回退之后,当匹配完前面几个字符,下一个要去匹配文本的字符就是X
                // ABABB         文本
                // ABABAC        模式
                // 比如说,在上面的匹配中,A没有匹配上B.在回退算法中,下一步就要从这种状态开始执行算法
                // ABABB         文本
                //  ABABAC       模式
                // 在经历了几次回退算法之后,文本和模式之间的关系会变成这样
                // ABABB         文本
                //   ABABAC      模式
                // 这是不是相当于,在第一次匹配失败之后,就直接尝试用A去匹配B.通过用A匹配B,我们可以得到用来匹配下一个文本字符的模式字符.
                dp[j][c] = dp[X][c];
                dp[j][pat.charAt(j)] = j + 1;
            }
            // 更新影子状态
            System.out.print(pat.charAt(j) + "使用的X是" + X + "   ");
            System.out.println("下一个元素的X是dp[" + X + "][pat.charAt(" + j + ")]=dp[" + X + "][" + pat.charAt(j) + "]=" + dp[X][pat.charAt(j)]);
            X = dp[X][pat.charAt(j)];
        }
    }

    public int search(String txt) {
        int M = pat.length();
        int N = txt.length();
        // pat 的初始态为 0
        int j = 0;
        for (int i = 0; i < N; i++) {
            // 计算 pat 的下一个状态
            j = dp[j][txt.charAt(i)];
            // 到达终止态，返回结果
            if (j == M) return i - M + 1;
        }
        // 没到达终止态，匹配失败
        return -1;
    }
}

