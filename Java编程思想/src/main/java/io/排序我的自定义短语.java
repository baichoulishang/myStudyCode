package io;

import io.netty.util.internal.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.groupingBy;


/**
 * @date 2020/3/1 18:54
 */
public class 排序我的自定义短语 {
    private static String readerFile = "F:\\Study\\自定义短语排序.txt";
    private static String writeFile = "F:\\Study\\out.txt";
    private static String startStr = ";  ss,1=#$year年$month月$day_dd日 $fullhour:$minute:$second";

    public static void main(String[] args) throws IOException {
        sortIt();
    }

    public static void sortIt() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(readerFile));
        BufferedWriter out = new BufferedWriter(new FileWriter(writeFile));

        List<String> result = new ArrayList<>();
        boolean start = false;
        String s;
        StringBuilder sb = new StringBuilder();
        // 一行一行读取
        while ((s = in.readLine()) != null) {
            if (StringUtil.isNullOrEmpty(s)) {
                continue;
            }
            if (start) {
                if ("sj".equals(getChar(s, 1))) {
                    out.write(s + "\n");
                } else {
                    result.add(s);
                }
            }
            if (startStr.equals(s)) {
                start = true;
            }
        }

        Map<String, List<String>> collect = result.stream().filter(e -> !StringUtil.isNullOrEmpty(e)).sorted((a, b) -> {
            String s1 = getChar(a, 1);
            String s2 = getChar(b, 1);
            if (s1.equals(s2)) {
                return getChar(a, 3).compareTo(getChar(b, 3));
            } else {
                return s1.compareTo(s2);
            }
        }).collect(groupingBy(e -> getChar(e, 1)));
        collect.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(ele -> {
            // ele是单独的元素
            AtomicInteger order = new AtomicInteger(1);
            ele.getValue().stream().forEach(str -> {
                // System.out.println(ele.getKey() + "," + (order.getAndIncrement()) + "=" + getChar(str, 3));
                try {
                    out.write(ele.getKey() + "," + (order.getAndIncrement()) + "=" + getChar(str, 3) + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        // 关闭流
        in.close();
        out.close();
    }


    public static String getChar(String str, int a) {
        if (a == 1) {
            return str.substring(0, str.indexOf(","));
        } else if (a == 2) {
            return str.substring(str.indexOf(",") + 1, str.indexOf("="));
        } else if (a == 3) {
            return str.substring(str.indexOf("=") + 1);
        }
        return null;
    }

    public static int findNumber(String str, String letter, int num) {
        int i = 0;
        int m = 0;
        char c = letter.charAt(0);
        char[] ch = str.toCharArray();
        for (int j = 0; j < ch.length; j++) {
            if (ch[j] == c) {
                i++;
                if (i == num) {
                    m = j;
                    break;
                }
            }
        }
        return m;
    }
}
