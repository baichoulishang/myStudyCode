package test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈宜康
 * @date 2020/1/12 14:07
 * @forWhat
 */
public class TreeVO {
    private String id;
    private String name;
    private String pid;
    private int code;
    private List<TreeVO> children = new ArrayList<>();

    @Override
    public String toString() {
        return "TreeVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public TreeVO() {

    }

    public TreeVO(String id, String name, String pid, int code) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<TreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVO> children) {
        this.children = children;
    }
}
