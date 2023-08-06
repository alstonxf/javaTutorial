package DEMO.MVC;

public class UserModel {
    private String username;
    private String email;

    public UserModel(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // getter和setter方法

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

