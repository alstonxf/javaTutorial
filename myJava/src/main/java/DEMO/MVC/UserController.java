package DEMO.MVC;

public class UserController {
    private UserModel userModel;
    private UserView userView;

    public UserController(UserModel userModel, UserView userView) {
        this.userModel = userModel;
        this.userView = userView;
    }

    public void updateUserDetails(String username, String email) {
        if (isValidEmail(email)) {
            userModel.setUsername(username);
            userModel.setEmail(email);
            userView.displayUserDetails(userModel.getUsername(), userModel.getEmail());
        } else {
            userView.displayErrorMessage("Invalid email format");
        }
    }

    private boolean isValidEmail(String email) {
        // 验证电子邮件的格式
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}

