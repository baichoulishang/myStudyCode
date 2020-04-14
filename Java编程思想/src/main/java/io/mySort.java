package baichoulishang.starteureka;

/**
 * @date 2020/3/3 0003 14:13
 */


import com.alibaba.excel.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.groupingBy;


/**
 * 排序自定义短语
 *
 * @date 2020/3/1 18:54
 */
public class mySort {
    private static String readerFile = "F:\\Study\\自定义短语排序.txt";
    private static String writeFile = "F:\\Study\\out.txt";
    private static String startStr = ";  ss,1=#$year年$month月$day_dd日 $fullhour:$minute:$second";
    private static Map<String, Integer> startMap = new HashMap<String, Integer>() {
        {
            put("you", 2);
            put("boy", 2);
            put("shi", 2);
            put("dao", 2);
            put("cha", 5);
        }
    };

    public static void main(String[] args) throws IOException {
        sortIt();
    }

    public static void sortIt() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(readerFile));
        BufferedWriter out = new BufferedWriter(new FileWriter(writeFile));

        List<String> result = new ArrayList<>();
        String s;
        // 一行一行读取
        while ((s = in.readLine()) != null) {
            if (StringUtils.isEmpty(s)) {
                continue;
            }
            if ("sj".equals(getChar(s, 1))) {
                out.write(s + "\n");
            } else {
                result.add(s);
            }
        }

        Map<String, List<String>> collect = result
                .stream()
                .filter(e -> !StringUtils.isEmpty(e))
                .sorted((a, b) -> {
                    String s1 = getChar(a, 1);
                    String s2 = getChar(b, 1);
                    return s1.equals(s2) ? getChar(a, 3).compareTo(getChar(b, 3)) : s1.compareTo(s2);
                }).collect(groupingBy(e -> getChar(e, 1)));
        collect.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(ele -> {
            AtomicInteger order;
            // ele是单独的元素
            if (startMap.containsKey(ele.getKey())) {
                order = new AtomicInteger(startMap.get(ele.getKey()));
            } else {
                order = new AtomicInteger(1);
            }
            ele.getValue().forEach(str -> {
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

}



