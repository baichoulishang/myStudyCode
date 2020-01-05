package calculate;

import calculate.pojo.Role;
import dataStructure.ArrayStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("fsdfsdf");
        words.add("fsdfsdfsd");
        words.add("fsdfsdfsdfssd");
        words.add("fsdfsdfsdfsdsd");
        words.add("fsdfsdfsdfsdfsd");
        List<String> results = new ArrayList();
        Optional<String> optionalS = words.stream().filter(s -> s.startsWith("f")).findFirst();
        optionalS.ifPresent(results::add);
        Optional<Boolean> add = optionalS.map(results::add);
        optionalS.get();
    }


    /**
     * 计算算数表达式的结果
     *
     * @param:
     * @return:
     * @date: 2019/4/5 16:20
     */
    public static void calculateString(String string) {
        ArrayStack ops = new ArrayStack(100);
        ArrayStack vals = new ArrayStack(100);
        String opsString = "+-*/sqrt";
        for (String s : string.split("")) {
            if (opsString.contains(s)) {
                ops.push(s);
            } else if ("(".equals(s)) {
            } else if (")".equals(s)) {
                String op = (String) ops.pop();//栈顶的运算符
                double val = Double.parseDouble((String) vals.pop());//栈顶的数
                switch (op) {
                    case "+":
                        val = Double.parseDouble((String) vals.pop()) + val;
                        break;
                    case "-":
                        val = Double.parseDouble((String) vals.pop()) - val;
                        break;
                    case "*":
                        val = Double.parseDouble((String) vals.pop()) * val;
                        break;
                    case "/":
                        val = Double.parseDouble((String) vals.pop()) / val;
                        break;
                    default:
                        val = Math.sqrt(val);
                        break;
                }
                vals.push(val);
            } else {
                //把数值压入栈
                vals.push(s);
            }
        }
        System.out.println(vals.pop());
    }


    public static boolean isSorted(String[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1].compareTo(a[i]) > 0) {
                return false;
            }
        }
        return true;
    }

    public static int indexOf(int[] a, int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static Role changeRole(Role role) {
        role.setNote("这是改变之后的note");
        return role;
    }


    public static Role getRole() {
        Role role = new Role();
        role.setId("20190405092112");
        role.setRolename("2019年04月05日 09.21.21");
        role.setNote("测试");
        return role;
    }

}
