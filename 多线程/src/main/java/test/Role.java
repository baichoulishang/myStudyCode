package test;

import java.util.Comparator;
import java.util.Date;

public class Role implements Comparable<Role>, Comparator<Role> {
    private String id;
    private String rolename;
    private String note;
    private Date cjsj;
    private int age;

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", rolename='" + rolename + '\'' +
                ", note='" + note + '\'' +
                ", cjsj=" + cjsj +
                ", age=" + age +
                '}';
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

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Role o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public int compare(Role o1, Role o2) {
        if (o1.getAge() > o2.getAge()) {
            return 1;
        } else {
            return -1;
        }
    }
}
