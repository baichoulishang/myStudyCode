/**
 * @author 陈宜康
 * @date 2020/1/11 16:37
 * @forWhat
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * @author 陈宜康
 * @date 2019/9/13 20:53
 * @forWhat
 */
public class CssSort {

    private static final String READFILE = "E:\\study\\project\\学习过程中的代码\\一个普通的web项目\\src\\main\\webapp\\core\\css\\table.css";
    private static final String WRITEFILE = "E:\\ideaProject\\shask\\all\\all-web\\src\\main\\webapp\\shznzx\\css\\znzxWeb.css";

    public static void main(String[] args) throws IOException {
        cssSort(READFILE);
    }

    public static void cssSort(String filename) throws IOException {
        // 先根据文件名,转化成FileReader对象
        // BufferedReader装饰器类
        // FileReader普通的InputStream类
        // 我们知道,装饰器类的基本用法,就是在装饰器的构造方法中传入原本的对象.装饰器对象
        // 在执行方法时,不仅会执行原本对象的方法,还会执行"修饰"的方法
        BufferedReader in = new BufferedReader(new FileReader(filename));
        Stack<String> stack = new Stack<>();
        String result = "";
        ArrayList<String> list = new ArrayList<>();
        int c = 0;
        while ((c = in.read()) != -1) {
            char at = (char) c;
            if ('\n' == at || '\r' == at) {
                continue;
            }
            result += at;
            if ('{' == at) {
                stack.push(String.valueOf(at));
            } else if ('}' == at) {
                stack.pop();
                if (stack.empty()) {
                    list.add(result.trim().replaceAll("\\s{2,}", " "));
                    result = "";
                }
            }
        }
        List<String> collect = list.stream().map(s -> s.replaceAll("\\/\\*[\\s\\S]*\\*\\/|\\/\\/.*", "")).collect(Collectors.toList());
        collect.sort((o1, o2) -> {
            for (int i = 0, _lenth = Math.min(o1.length(), o2.length()); i < _lenth; i++) {
                char a1 = o1.charAt(i);
                char a2 = o2.charAt(i);
                if (a1 == a2) {
                    continue;
                } else {
                    boolean match_1 = String.valueOf(a1).matches("[a-zA-Z]+");
                    boolean match_2 = String.valueOf(a2).matches("[a-zA-Z]+");
                    // 其中一个是符号
                    if (match_1 != match_2) {
                        if (match_1 == true) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        if (match_1 == false) {
                            // 不相等,且都是符号
                            if (a1 == '{') {
                                return -1;
                            } else if (a2 == '{') {
                                return 1;
                            } else {
                                return o1.compareTo(o2);
                            }
                        } else {
                            // 都是字母
                            return o1.compareTo(o2);
                        }
                    }
                }
            }
            return o1.compareTo(o2);
        });
        for (String str : collect) {
            System.out.println(str);
        }
        // 关闭流
        in.close();
        PrintWriter out = new PrintWriter(READFILE);
        for (String str : collect) {
            out.println(str);
        }
        out.close();
    }
}

