package test;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SuperUtil {


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException {

        List<String> typeList = Arrays.asList("Short", "Integer", "Long");

        String[] names = {"1", "2"};
        List<String> nameList = new ArrayList<>();
        nameList.addAll(Arrays.asList(names));
        nameList.remove(1);
    }


    private static void interatorMap(Map map) {
        Set set = map.entrySet();
        for (Object o : set) {
            System.out.println("key是:" + o + "  值是:" + map.get(o));
        }
    }


    private static int[] getRandomOrder2(int need, int max) {
        if (need > max) {
            need = max;
        }
        long start = System.nanoTime();
        Random random = new Random();
        int[] arr = new int[max];
        int i;
        //费雪耶兹置乱算法
        //每次生成的随机交换位置:
        for (i = arr.length - 1; i > max - need - 1; i--) {
            //随机数生成器，范围[0, i]
            int rand = random.nextInt(i + 1);
            if (arr[i] == 0) {
                arr[i] = i + 1;
            }
            if (arr[rand] == 0) {
                arr[rand] = rand + 1;
            }
            if (rand != i) {
                int temp = arr[i];
                arr[i] = arr[rand];
                arr[rand] = temp;
            }
        }
        random = null;
        return Arrays.copyOfRange(arr, max - need, max);
    }

    private static long getRandomOrder(int n, int max) {
        long start = System.nanoTime();
        if (n >= max) {
            n = max;
        }
        // 首先给max++
        max++;
        Set<Integer> r = new LinkedHashSet<Integer>();
        Random random = new Random();
        while (r.size() < n) {
            int i = random.nextInt(max);
            if (i > 0) {
                r.add(i);
            }
        }
        long end = System.nanoTime();
        // 帮助GC回收对象
        random = null;
        return end - start;
    }


    private static long randomNum2(int max, int need) {
        long start = System.nanoTime();
        Random random = new Random();
        int[] arr = new int[max];
        int i;
        //费雪耶兹置乱算法
        //每次生成的随机交换位置:
        for (i = arr.length - 1; i > max - need - 1; i--) {
            //随机数生成器，范围[0, i]
            int rand = random.nextInt(i + 1);
            if (arr[i] == 0) {
                arr[i] = i + 1;
            }
            if (arr[rand] == 0) {
                arr[rand] = rand + 1;
            }
            if (rand != i) {
                int temp = arr[i];
                arr[i] = arr[rand];
                arr[rand] = temp;
            }
        }
        int[] ints = Arrays.copyOfRange(arr, max - need, max);
        long end = System.nanoTime();
        // 帮助GC回收对象
        random = null;
        return end - start;
    }

    private static long randomNum(int max, int need) {
        long start = System.nanoTime();
        Random random = new Random();
        int[] arr = new int[max];
        int i;
        //初始的有序数组
        for (i = 0; i < max; i++) {
            arr[i] = i + 1;
        }
        //费雪耶兹置乱算法
        //每次生成的随机交换位置:
        for (i = arr.length - 1; i > max - need; i--) {
            //随机数生成器，范围[0, i]
            int rand = random.nextInt(i + 1);
            int temp = arr[i];
            arr[i] = arr[rand];
            arr[rand] = temp;
        }
        int[] ints = Arrays.copyOfRange(arr, max - need, max);
        long end = System.nanoTime();
        // 帮助GC回收对象
        random = null;
        return end - start;
    }


    /**
     * 列转行要用到的输出语句
     *
     * @param num
     */
    public static void lineToRow(int num) {
        String a = "";
        String b = "";
        String c = "";
        for (int i = 1; i < num + 1; i++) {
            a += "'" + i + "' \"" + i + "\",";
            b += "\"" + i + "\",";
            c += "sum(\"" + i + "\"),";
        }
        System.out.println(a.substring(0, a.length() - 1) + "\n\n\n");
        System.out.println(b.substring(0, b.length() - 1) + "\n\n\n");
        System.out.println(c.substring(0, c.length() - 1) + "\n\n\n");
    }

    public static void saveRegionTreeInfo(String id) {

    }

    /**
     * 查找某个字符串所在的位置
     * Author:陈宜康
     *
     * @param str    String
     * @param letter 要查询的字符
     * @param num    第几个
     * @return 2018-4-8 上午10:29:42
     */
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

    /**
     * 自动分割字符串
     *
     * @param sqlnew
     */
    public static void autoSegmentation(String sqlnew) {
        StringBuilder sql = new StringBuilder(sqlnew);
        String sqlWithFilter = sql.toString().replace("\"", "双引号");
        String sqlString = sqlWithFilter.replace("\n", " ");
        for (int i = 1; i < 100; i++) {
            String shit = "";
            for (int j = 0; j < i; j++) {
                shit += " ";
            }
            sqlString = sqlString.replace(shit, " ");
        }
        String shit2 = sqlString;
        for (int i = 1; i < 100; i++) {
            String hehe = "";
            for (int j = 0; j < i; j++) {
                hehe += " ";
            }
            shit2 = shit2.replace(hehe, " ");
        }
        System.out.println(makelinefeed(shit2).replace("双引号", "\\\"").toUpperCase() + ";");
    }

    public static String createVO(String sql) {
        String hehe = "";
        String[] shit = sql.split("\n");
        for (String string :
                shit) {
            List<String> fuck = Arrays.asList(string.split("\t"));
            hehe += "//";
            hehe += fuck.get(2);
            hehe += "\n";
            String checkWord = fuck.get(1).substring(0, 4).toUpperCase();
            if ("VARC".equals(checkWord) ||
                    "NUMB".equals(checkWord) ||
                    "CHAR".equals(checkWord)) {
                hehe += "private String ";
            } else {
                hehe += "private Date ";
            }
            hehe += fuck.get(0).toLowerCase();
            hehe += ";\n";
        }
        System.out.println(hehe);
        return null;
    }

    public static String makelinefeed(String s) {
        String[] str = s.split(" ");
        StringBuffer buffer = new StringBuffer();
        int len = 0;
        for (int i = 0; i < str.length; i++) {
            len += str[i].length();
            if (len > 80) {
                buffer.append(" \" +\n\" " + str[i] + " ");//利用StringBuffer对字符串进行修改
                len = str[i].length() + 1;//+1为换行后读出空格一位
            } else {
                buffer.append(str[i] + " ");
                len++;
            }
        }
        return "\" " + buffer.toString() + " \"";
    }

    /**
     * 拼凑sql用的
     *
     * @param sqlO   sql
     * @param paramO 参数
     * @return
     */
    public static String combineSql(String sqlO, List paramO) {
        {
            //传入sql语句
            StringBuilder sqlOld = new StringBuilder(sqlO);
            sqlOld.append("####");
            String sql = sqlOld.toString();
            //参数
            String paramOld = paramO.toString();
            String param = paramOld.substring(1, paramOld.length() - 1);
            param += "###";
            String[] sqlList = sql.split("\\?");
            String[] paramList = param.split(", ");
            String sqlNew = sqlList[0];
            if ((sqlList.length - 1) == paramList.length) {
                for (int i = 0; i < paramList.length; i++) {
                    if (i == paramList.length - 1) {
                        if ("###".equals(paramList[i])) {
                            paramList[i] = "";
                        } else {
                            paramList[i] = paramList[i].substring(0, paramList[i].length() - 3);
                        }
                    }
                    if (paramO.get(i) instanceof Integer) {
                        sqlNew += "" + paramList[i] + "" + sqlList[i + 1];
                    } else {
                        sqlNew += "'" + paramList[i] + "'" + sqlList[i + 1];

                    }
                }
            }
            sqlNew = sqlNew.substring(0, sqlNew.length() - 4);
            System.out.println(sqlNew.replace("\n", " "));
            return sqlNew;
        }
    }

    /**
     * 将map中所有的key或者entry以","连接并返回
     *
     * @param map
     * @param keyOrEntry true:得到key;false:得到entry
     * @return
     */
    public static String getMapKeyOrEntryList(Map map, boolean keyOrEntry) {
        String value = "";
        if (map.isEmpty()) {
            System.out.println("传入的map为空");
        } else {
            Set<String> set = new HashSet<>();
            if (keyOrEntry) {
                set = map.keySet();
            } else {
                set = map.entrySet();
            }
            for (String string :
                    set) {
                value += string + ",";
            }
        }
        return value;
    }

    /**
     * 根据传入的正则表达式进行匹配
     *
     * @param regex
     * @param orginal
     * @return
     */
    public static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    /**
     * 判读是否是实数.判断程序最多,能不用则不用
     *
     * @param orginal
     * @return
     */
    public static boolean isRealNumber(String orginal) {
        return isWholeNumber(orginal) || isDecimal(orginal);
    }

    /**
     * 未知
     *
     * @param orginal
     * @return
     */
    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    /**
     * 判断是否是小数
     *
     * @param orginal
     * @return
     */
    public static boolean isDecimal(String orginal) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    /**
     * 判断是否是正小数
     *
     * @param orginal
     * @return
     */
    public static boolean isPositiveDecimal(String orginal) {

//        String regExp = "^[1-9][0-9]*(\\.[0-9]{1,"+2+"})?$"; //n为小数位数
//        Pattern p = Pattern.compile(regExp);
//        Matcher m = p.matcher(str);
//        return m.matches();

        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
//        return isMatch("^[1-9][0-9]*(\\.[0-9]{1,"+2+"})?$", orginal);
    }

    public static int countIncludeNum(String sql, String include) {
        int count = sql.length() - sql.replace(include, "").length();
        return count;
    }

    /**
     * 判断是否是正整数
     *
     * @param orginal
     * @return
     */
    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    /**
     * 判断是否是小于decimalBit位数的正数
     *
     * @param orginal    要判断的数值
     * @param decimalBit 最多几位小数
     * @return
     */
    public static boolean isPositiveIntegerOrDecimal(String orginal, int decimalBit) {
        //^[1-9][0-9]*(\.[0-9]{1,0})?$

        return isMatch("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0," + decimalBit + "})?$", orginal);
//        return isMatch("^[1-9][0-9]*(\\.[0-9]{1,"+decimalBit+"})?$", orginal);
    }

    /**
     * 未知
     *
     * @param orginal
     * @return
     */
    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    public static void buildTable(String sql) {
        sql.replace("白", "哈利路亚");
        sql += "\n";
        sql = sql.replace("\t\t\t\n", "\n").replace("\t\t\n", "\n").
                replace("\t\n", "\n").replace("\t\t\t", "白").replace("\t\t", "白").
                replace("\t", "白").replace("：", ":").toUpperCase();
        List<String> sqlList = Arrays.asList(sql.split("\n"));
        String chName = sqlList.get(1);
        chName = chName.substring(findNumber(chName, ":", 1) + 1);
        String enName = sqlList.get(0);
        enName = enName.substring(findNumber(enName, ":", 1) + 1);
        List<String> newlist = new ArrayList<>();
        for (int i = 3; i < sqlList.size(); i++) {
            newlist.add(sqlList.get(i));
        }
        String shit = "-- " + chName + "\nCREATE table " + enName + " ( \n";
        String column = "";
        String allowNull = "";
        for (int i = 0; i < newlist.size(); i++) {
            column = newlist.get(i);
            if (countIncludeNum(column, "白") == 4) {
                allowNull = column.substring(findNumber(column, "白", 3) + 1, findNumber(column, "白", 4));
            } else {
                allowNull = column.substring(findNumber(column, "白", 3) + 1);
            }
            shit += column.substring(0, findNumber(column, "白", 1)) + " ";
            shit += column.substring(findNumber(column, "白", 2) + 1, findNumber(column, "白", 3));
            if ("N".equals(allowNull)) {
                shit += " not null";
            }
            if (i == 0) {
                shit += " PRIMARY KEY ";
            }
            if (i != newlist.size() - 1) {
                shit += ",\n";
            } else {
                shit += "\n); ";
            }
        }
        shit += "\nCOMMENT ON TABLE " + enName + " IS '" + chName + "';\n";
        for (int i = 0; i < newlist.size(); i++) {
            column = newlist.get(i);
            shit += "COMMENT ON COLUMN " + enName + "." + column.substring(0, findNumber(column, "白", 1)) + " is '" +
                    column.substring(findNumber(column, "白", 1) + 1, findNumber(column, "白", 2));
            if (countIncludeNum(column, "白") == 4) {
                shit += "," + column.substring(findNumber(column, "白", 4) + 1);
            }
            shit += "';\n";
        }
        System.out.println(shit.toUpperCase().replace("哈利路亚", "白"));
    }

    public void createTable(String sqlOld) {
        buildTable(sqlOld);

    }

    /**
     * 通过反射来赋值
     *
     * @param fieldName 字段名字
     * @param value     值
     * @param object    对象
     * @return
     */
    public Object setByReflect(String fieldName, String value, Object object) throws NoSuchFieldException, IllegalAccessException {
        Field f = object.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(object, value);
        return object;
    }


//    1、正则表达式
//
//"^\\d+$"　　//非负整数（正整数 + 0）
//        "^[0-9]*[1-9][0-9]*$"　　//正整数
//        "^((-\\d+)|(0+))$"　　//非正整数（负整数 + 0）
//        "^-[0-9]*[1-9][0-9]*$"　　//负整数
//        "^-?\\d+$"　　　　//整数
//        "^\\d+(\\.\\d+)?$"　　//非负浮点数（正浮点数 + 0）
//        "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$"　　//正浮点数
//        "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$"　　//非正浮点数（负浮点数 + 0）
//        "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$"　　//负浮点数
//        "^(-?\\d+)(\\.\\d+)?$"　　//浮点数
//
//        2、使用方法
//    var r = /^\+?[1-9][0-9]*$/;　　//正整数
//    String str = "123";
//    boolean flag=r.test(str);
//    如果判断为正整数，则flag为true
//
//3、JS整数相加
//
//            首先保证输入的都是数字
//    nText1=parseFloat(document.all.text1.value);
//    nText2=parseFloat(document.all.text2.value);
//    nSum=nText1+nText2

    /**
     * 通过反射获得对象的某个属性
     *
     * @param fieldName 属性的名字
     * @param object    对象
     * @return
     * @throws Exception
     */
    public String reflect(String fieldName, Object object) throws Exception {
        Class c = object.getClass();
        // 获取该对象的propertyName成员变量
        Field field = c.getDeclaredField(fieldName);
        // 取消访问检查
        field.setAccessible(true);
        // 给对象的成员变量赋值为指定的值
        return (String) field.get(object);
    }

    static class TaskMonitor {
        Integer i = 0;
        private Integer count;

        public TaskMonitor(int count) {
            this.count = count;
        }

        public synchronized void markSuccess() {
            i = i + 1;
        }

        public Integer count() {
            return i;
        }

        public Boolean isComplete() {
            return count().intValue() >= count.intValue();
        }
    }
}

