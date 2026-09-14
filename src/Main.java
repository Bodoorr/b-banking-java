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
        loginService.login(id,password);


    }
}
