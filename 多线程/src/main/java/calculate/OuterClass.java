package calculate;

public class OuterClass {
    static {
        System.out.println("静态代码块.在构造方法之前执行.");
    }

    public OuterClass() {
        System.out.println("无参的构造方法");
    }

    public OuterClass(String flag) {
        final String hehe = "";
        System.out.println("带参的构造方法:" + flag);
    }

    public static OuterClass getInstatnce() {
        return InnerStaticClass.out;
    }

    public static void main(String[] args) {
        InnerStaticClass.load();
        OuterClass out = InnerStaticClass.out;
        //普通内部类的实例化方式
        InnerClass innerClass = out.new InnerClass();
    }

    static class InnerStaticClass {
        private static OuterClass out = new OuterClass("innerStatic");

        static {
            System.out.println("静态内部类的静态代码块");
        }

        private static void load() {
            System.out.println("静态代码块的内部方法");
        }
    }

    class InnerClass {
        private OuterClass out = new OuterClass("inner");
    }

}
