package DEMO.MVC;

public class UserView {
    public void displayUserDetails(String username, String email) {
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
    }

    public void displayErrorMessage(String message) {
        System.out.println("Error: " + message);
    }
}
