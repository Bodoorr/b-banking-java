import models.Account;
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
                    if (customerService.customerIdExisting(customerId)){
                        System.out.println("Customer Id already exist.");
                        return;
                    }
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
                        if (customerService.accountIdExisting(checkingAccountId)){
                            System.out.println("Checking ID already exist!");
                            return;
                        }
                            System.out.print("Enter Checking Account Balance: ");
                        checkingAccountBalance= scanner.nextDouble();
                    } else if (accountChoice.equals("2")) {
                        accountType = "SAVINGS";
                        System.out.print("Enter Account ID: ");
                        savingsAccountId= scanner.next();
                        if (customerService.accountIdExisting(savingsAccountId)){
                            System.out.println("Savings ID already exist!");
                            return;
                        }
                        System.out.print("Enter Savings Account Balance: ");
                        savingsAccountBalance= scanner.nextDouble();
                    } else if (accountChoice.equals("3")) {
                        accountType = "BOTH";
                        System.out.print("Enter Checking Account ID: ");
                        checkingAccountId= scanner.next();
                        if (customerService.accountIdExisting(checkingAccountId)){
                            System.out.println("Checking ID already exist!");
                            return;
                        }
                        System.out.print("Enter Checking Account Balance: ");
                        checkingAccountBalance= scanner.nextDouble();
                        System.out.print("Enter Savings Account ID: ");
                        savingsAccountId= scanner.next();
                        if (customerService.accountIdExisting(savingsAccountId) || savingsAccountId.equals(checkingAccountId)){
                            System.out.println("This account ID cannot be used. Each account suppose to have different ID's.");
                            return;
                        }
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
            AccountService accountService=new AccountService();
            ArrayList<Account> accounts=accountService.getAccounts(user.getId());

                if (accounts.isEmpty()){
                    System.out.println("You don't have any accounts.");
                    return;
                }
                Account selectedAccount;
                if (accounts.size()==1){
                    selectedAccount= accounts.get(0);
                } else {
                    System.out.println("Select Account: ");
                    System.out.println("1.Checking Account.");
                    System.out.println("2. Savings Account.");
                    String selectAccountOption= scanner.next();
                    if (selectAccountOption.equals("1")){
                        selectedAccount= accounts.get(0);
                    } else if(selectAccountOption.equals("2")){
                        selectedAccount= accounts.get(1);
                    } else {
                        System.out.println("wrong input! please try again.");
                        return;
                    }
                }

                System.out.println("Choose Transaction: ");
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Transfer");
                System.out.println("4. Exit");
                String transactionOption= scanner.next();
                if (transactionOption.equals("1")){
                    System.out.print("Enter deposit amount: ");
                    double depositAmount= scanner.nextDouble();
                    accountService.deposit(selectedAccount.getAccountId(),depositAmount);
                }else if (transactionOption.equals("2")){
                    System.out.print("Enter withdraw amount: ");
                    double withdrawAmount= scanner.nextDouble();
                    accountService.withdraw(selectedAccount.getAccountId(),withdrawAmount);
                }else if (transactionOption.equals("3")){
                    System.out.println("1. Transfer to my other account.");
                    System.out.println("2. Transfer to other customer account");
                    System.out.println("3. Exit");
                    String transferAccountChoice= scanner.next();
                    if (transferAccountChoice.equals("1")){
                        if (accounts.size() < 2){
                            System.out.println("You don't have another account.");
                            return;
                        }
                        Account toAccount;
                        if (selectedAccount==accounts.get(0)){
                            toAccount= accounts.get(1);
                        } else {
                            toAccount= accounts.get(0);
                        }
                        System.out.println("Enter transfer amount: ");
                        double transferAmount= scanner.nextDouble();
                        accountService.transfer(selectedAccount.getAccountId(),toAccount.getAccountId(),transferAmount);
                    } else if(transferAccountChoice.equals("2")){
                        System.out.print("Enter the receiver account id: ");
                        String receiverAccountId= scanner.next();
                        System.out.println("Enter transfer amount: ");
                        double transferAmount= scanner.nextDouble();
                        accountService.transfer(selectedAccount.getAccountId(), receiverAccountId,transferAmount);
                    }else if (transferAccountChoice.equals("3")){
                        System.out.println("Bye!");
                    } else{
                        System.out.println("Invalid input! Please try again.");
                        return;
                    }

                }else if (transactionOption.equals("4")){
                    System.out.println("Bye!");
                } else {
                    System.out.println("Invalid input, Please try again.");
                    return;
                }
            }

        }else{
            System.out.println("Login failed! try again.");
        }


    }
}
