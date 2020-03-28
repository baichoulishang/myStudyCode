package pojo;

import org.apache.poi.ss.formula.functions.T;

import javax.management.relation.RoleList;
import java.util.ArrayList;
import java.util.List;

public class Role implements Comparable {
    public void main(String[] args) {

        check(new ArrayList<Role>());
    }

    public void check(List<? extends Role> t) {

    }

    private String id;
    private String rolename;
    private String note;
    private int age;

    public Role() {
    }


    public Role(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
