package DEMO.MVC;

public class Main {
    public static void main(String[] args) {
        // 创建模型、视图和控制器对象
        UserModel user = new UserModel("John Doe", "johndoe@example.com");
        UserView userView = new UserView();
        UserController userController = new UserController(user, userView);

        // 显示用户详情
        userView.displayUserDetails(user.getUsername(), user.getEmail());

        // 更新用户详情
        userController.updateUserDetails("Jane Smith", "janesmith@example.com");
    }
}
