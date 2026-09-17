package services;

import models.CheckingAccount;
import models.Customer;
import models.SavingsAccount;
import utils.FileManager;
import utils.PasswordUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerService {
    public void addCustomer(String id, String firstName, String lastName, String password, String accountType,String checkingAccountId, double checkingAccountBalance ,String checkingCardType, String savingsAccountId, double savingsAccountBalance, String savingsCardType) throws NoSuchAlgorithmException {

        String hashPassword= PasswordUtils.hashPassword(password);
        Customer customer=new Customer(id,firstName,lastName, password, "C");

        String customerData= id+","+firstName+","+lastName+","+hashPassword+","+"C";

        ArrayList<String> customerList= new ArrayList<>();
        customerList.add(customerData);
        FileManager.writeData("src/data", customerList);

        ArrayList<String> accountList= new ArrayList<>();

        if(accountType.equals("CHECKING")){
        CheckingAccount checkingAccount=new CheckingAccount(checkingAccountId,checkingAccountBalance,0,true, checkingCardType);

        customer.addAccount(checkingAccount);

        String accountData= checkingAccountId+","+id+","+accountType+","+checkingAccountBalance+","+"0"+","+"true"+","+checkingCardType;

        accountList.add(accountData);
    }
        else if (accountType.equals("SAVINGS")) {
        SavingsAccount savingsAccount=new SavingsAccount(savingsAccountId,savingsAccountBalance,0,true,savingsCardType);

        customer.addAccount(savingsAccount);

        String accountData= savingsAccountId+","+id+","+accountType+","+savingsAccountBalance+","+"0"+","+"true"+","+savingsCardType;

        accountList.add(accountData);

        } else if (accountType.equals("BOTH")){
        CheckingAccount checkingAccount=new CheckingAccount(checkingAccountId,checkingAccountBalance,0,true,checkingCardType);

        SavingsAccount savingsAccount=new SavingsAccount(savingsAccountId,savingsAccountBalance,0,true,savingsCardType);

        customer.addAccount(checkingAccount);
        customer.addAccount(savingsAccount);

        String checkingAccountData= checkingAccountId+","+id+","+"CHECKING"+","+checkingAccountBalance+","+"0"+","+"true"+","+checkingCardType;

        String savingsAccountData= savingsAccountId+","+id+","+"SAVINGS"+","+savingsAccountBalance+","+"0"+","+"true"+","+savingsCardType;

        accountList.add(checkingAccountData);
        accountList.add(savingsAccountData);

        }
        FileManager.writeData("src/accounts", accountList);
    }

    public String generateCustomerId(){
        Random random=new Random();
        String customerId= "C"+(1000 + random.nextInt(9000));

        while (customerIdExisting(customerId)){
            customerId= "C"+(1000 + random.nextInt(9000));
        }
        return customerId;
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

    public String generateAccountId(){
        Random random=new Random();
        String accountId= "A"+(1000 + random.nextInt(9000));

        while (accountIdExisting(accountId)){
            accountId= "A"+(1000 + random.nextInt(9000));
        }
        return accountId;
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


    public void changePassword(String id, String newPassword){
        try {
            List<String> lines=FileManager.readAllLines("src/data");
            ArrayList<String> updatedLines=new ArrayList<>();

            for (String line: lines){
                String [] data=line.split(",");

                if (data[0].equals(id)){
                    String hashPassword= PasswordUtils.hashPassword(newPassword);

                    String updateLine= data[0]+","+data[1]+","+data[2]+","+hashPassword+","+data[4];

                    updatedLines.add(updateLine);

                } else {
                    updatedLines.add(line);
                }
            }

            FileManager.overWriteFile("src/data",updatedLines);
        } catch (IOException | NoSuchAlgorithmException  e) {
            throw new RuntimeException(e);
        }
    }
}
