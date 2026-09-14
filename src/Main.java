import models.User;
import services.AccountService;
import services.CustomerService;
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
        CustomerService customerService=new CustomerService();

        Optional<User> loggedIn= loginService.login(id, password);
        if (loggedIn.isPresent()){
            User user=loggedIn.get();
            System.out.println("Welcome "+ user.getFirstName() + " "+ user.getLastName());

            if (user.getRole().equals("B")){
                System.out.println("1. Add New Customer");
                System.out.println("2. Exit");
                System.out.println("Choose an option:");
                String option= scanner.next();

                if (option.equals("1")){
                    System.out.println("Enter Customer ID: ");
                    String customerId= scanner.next();
                    System.out.println("Enter Customer First Name: ");
                    String customerFirstName= scanner.next();
                    System.out.println("Enter Customer Last Name: ");
                    String customerLastName= scanner.next();
                    System.out.println("Enter Customer Password: ");
                    String customerPassword= scanner.next();
                    System.out.println("Choose Customer Account Type: ");
                    System.out.println("1.Checking Account.");
                    System.out.println("2.Savings Account.");
                    System.out.println("3.Both.");
                    String accountChoice= scanner.next();
                    String accountType;
                    String checkingAccountId="";
                    String savingsAccountId="";
                    double checkingAccountBalance=0;
                    double savingsAccountBalance=0;
                    if (accountChoice.equals("1")) {
                        accountType = "CHECKING";
                        System.out.print("Enter Account ID: ");
                        checkingAccountId= scanner.next();
                        System.out.print("Enter Checking Account Balance: ");
                        checkingAccountBalance= scanner.nextDouble();
                    } else if (accountChoice.equals("2")) {
                        accountType = "SAVINGS";
                        System.out.print("Enter Account ID: ");
                        savingsAccountId= scanner.next();
                        System.out.print("Enter Savings Account Balance: ");
                        savingsAccountBalance= scanner.nextDouble();
                    } else if (accountChoice.equals("3")) {
                        accountType = "BOTH";
                        System.out.print("Enter Checking Account ID: ");
                        checkingAccountId= scanner.next();
                        System.out.print("Enter Checking Account Balance: ");
                        checkingAccountBalance= scanner.nextDouble();
                        System.out.print("Enter Savings Account ID: ");
                        savingsAccountId= scanner.next();
                        System.out.print("Enter Savings Account Balance: ");
                        savingsAccountBalance= scanner.nextDouble();
                    } else {
                        System.out.println("Invalid Account Type.");
                        return;
                    }
                    customerService.addCustomer(customerId,customerFirstName,customerLastName,customerPassword,accountType,checkingAccountId, checkingAccountBalance,savingsAccountId,savingsAccountBalance);
                    System.out.println("Customer added successfully.");
                } else if (option.equals("2")) {
                    System.out.println("Bye!");
                    System.exit(0);
                } else {
                    System.out.println("Invalid input.");
                }
            }else if (user.getRole().equals("C")){

            }

        }else{
            System.out.println("Login failed! try again.");
        }


    }
}
