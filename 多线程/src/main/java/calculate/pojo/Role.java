package calculate.pojo;

public class Role implements Comparable {
    private String id;
    private String rolename;
    private String note;

    public Role() {
    }

    public Role(String id, String rolename, String note) {
        this.id = id;
        this.rolename = rolename;
        this.note = note;
    }

    @Override
    public String toString() {
        return "pojo.Role{" +
                "id='" + id + '\'' +
                ", rolename='" + rolename + '\'' +
                ", note='" + note + '\'' +
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


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
