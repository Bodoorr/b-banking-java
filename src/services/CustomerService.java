package services;

import models.CheckingAccount;
import models.Customer;
import models.SavingsAccount;
import utils.FileManager;
import java.util.ArrayList;

public class CustomerService {
    public void addCustomer(String id, String firstName, String lastName, String password, String accountType,String checkingAccountId, double checkingAccountBalance , String savingsAccountId, double savingsAccountBalance){
        Customer customer=new Customer(id,firstName,lastName, password, "C");
        String customerData= id+","+firstName+","+lastName+","+password+","+"C";
        ArrayList<String> customerList= new ArrayList<>();
        customerList.add(customerData);
        FileManager.writeData("src/data", customerList);

        ArrayList<String> accountList= new ArrayList<>();
        if(accountType.equals("CHECKING")){
        CheckingAccount checkingAccount=new CheckingAccount(checkingAccountId,checkingAccountBalance);
        customer.addAccount(checkingAccount);
        String accountData= checkingAccountId+","+id+","+accountType+","+checkingAccountBalance;
        accountList.add(accountData);
    } else if (accountType.equals("SAVINGS")) {
        SavingsAccount savingsAccount=new SavingsAccount(savingsAccountId,savingsAccountBalance);
        customer.addAccount(savingsAccount);
        String accountData= savingsAccountId+","+id+","+accountType+","+savingsAccountBalance;
        accountList.add(accountData);
        } else if (accountType.equals("BOTH")){
        CheckingAccount checkingAccount=new CheckingAccount(checkingAccountId,checkingAccountBalance);
        SavingsAccount savingsAccount=new SavingsAccount(savingsAccountId,savingsAccountBalance);
        customer.addAccount(checkingAccount);
        customer.addAccount(savingsAccount);
        String checkingAccountData= checkingAccountId+","+id+","+"CHECKING"+","+checkingAccountBalance;
        String savingsAccountData= savingsAccountId+","+id+","+"SAVINGS"+","+savingsAccountBalance;
        accountList.add(checkingAccountData);
        accountList.add(savingsAccountData);
        }

        FileManager.writeData("src/accounts", accountList);

    }
}
