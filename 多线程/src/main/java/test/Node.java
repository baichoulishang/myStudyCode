package test;

/**
 * @author 陈宜康
 * @date 2019/11/30 10:14
 * @forWhat
 */
public class Node {
    //键
    Object key;
    //值
    Object value;
    //上一个节点
    Node pre;
    //下一个节点
    Node next;

    public Node(Object key, Object value) {
        this.key = key;
        this.value = value;
    }
}