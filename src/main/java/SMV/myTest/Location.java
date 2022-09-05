package SMV.myTest;

public class Location {
//    查询到的结果集，sql语句的返回字段。
    private int id;
    private String location;

    public Location(int id, String location, String pwd) {
        this.id = id;
        this.location = location;
    }

    public Location() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }
    

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", location='" + location + 
                '}';
    }
}

