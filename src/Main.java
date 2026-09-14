import models.User;
import services.LoginService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Login to your Account");
        System.out.print("Enter ID: ");
        String id= scanner.next();
        System.out.print("Enter Password: ");
        String password= scanner.next();


        LoginService loginService=new LoginService();

        Optional<User> loggedIn= loginService.login(id, password);
        if (loggedIn.isPresent()){
            User user=loggedIn.get();
            System.out.println("Welcome "+ user.getFirstName() + " "+ user.getLastName());
        }else{
            System.out.println("Logged in failed! try again.");
        }




    }
}
