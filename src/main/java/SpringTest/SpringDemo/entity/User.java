package SpringTest.SpringDemo.entity;

public class User {
//    public User(String name,Integer age){
//
//    }
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void init() {
        System.out.println("初始");
    }

    public void destory() {
        System.out.println("销毁");
    }
}