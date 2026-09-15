package services;

import models.CheckingAccount;
import models.Customer;
import models.SavingsAccount;
import utils.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    public void addCustomer(String id, String firstName, String lastName, String password, String accountType,String checkingAccountId, double checkingAccountBalance , String savingsAccountId, double savingsAccountBalance){
        Customer customer=new Customer(id,firstName,lastName, password, "C");
        String customerData= id+","+firstName+","+lastName+","+password+","+"C";
        ArrayList<String> customerList= new ArrayList<>();
        customerList.add(customerData);
        FileManager.writeData("src/data", customerList);

        ArrayList<String> accountList= new ArrayList<>();
        if(accountType.equals("CHECKING")){
        CheckingAccount checkingAccount=new CheckingAccount(checkingAccountId,checkingAccountBalance,0,true);
        customer.addAccount(checkingAccount);
        String accountData= checkingAccountId+","+id+","+accountType+","+checkingAccountBalance+","+"0"+","+"true";
        accountList.add(accountData);
    } else if (accountType.equals("SAVINGS")) {
        SavingsAccount savingsAccount=new SavingsAccount(savingsAccountId,savingsAccountBalance,0,true);
        customer.addAccount(savingsAccount);
        String accountData= savingsAccountId+","+id+","+accountType+","+savingsAccountBalance+","+"0"+","+"true";
        accountList.add(accountData);
        } else if (accountType.equals("BOTH")){
        CheckingAccount checkingAccount=new CheckingAccount(checkingAccountId,checkingAccountBalance,0,true);
        SavingsAccount savingsAccount=new SavingsAccount(savingsAccountId,savingsAccountBalance,0,true);
        customer.addAccount(checkingAccount);
        customer.addAccount(savingsAccount);
        String checkingAccountData= checkingAccountId+","+id+","+"CHECKING"+","+checkingAccountBalance+","+"0"+","+"true";
        String savingsAccountData= savingsAccountId+","+id+","+"SAVINGS"+","+savingsAccountBalance+","+"0"+","+"true";
        accountList.add(checkingAccountData);
        accountList.add(savingsAccountData);
        }

        FileManager.writeData("src/accounts", accountList);

    }
    public boolean customerIdExisting(String id){
        try {
            List<String> users= FileManager.readAllLines("src/data");
            for (String user: users){
                String [] data= user.split(",");
                if (data[0].equals(id)){
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public boolean accountIdExisting(String accountId){
        try {
            List<String> accounts= FileManager.readAllLines("src/accounts");
            for (String account:accounts){
                String [] data = account.split(",");
                if (data[0].equals(accountId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
