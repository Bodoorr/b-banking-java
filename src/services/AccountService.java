package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.CheckingAccount;
import models.SavingsAccount;
import utils.FileManager;
import models.Account;
public class AccountService {

    public ArrayList<Account> getAccounts(String customerId){
        ArrayList<Account> accountsList= new ArrayList<>();
        try {
            List<String> lines= FileManager.readAllLines("src/accounts");

            for (String line:lines){
                String [] data= line.split(",");
                if (data[1].equals(customerId)){
                    double balance= Double.parseDouble(data[3]);
                    int overdraftCount= Integer.parseInt(data[4]);
                    boolean accountActive= Boolean.parseBoolean(data[5]);

                    if (data[2].equals("CHECKING")){
                        CheckingAccount checkingAccount=new CheckingAccount(data[0],balance,overdraftCount,accountActive);
                        accountsList.add(checkingAccount);
                    }else if(data[2].equals("SAVINGS")){
                        SavingsAccount savingsAccount=new SavingsAccount(data[0],balance,overdraftCount,accountActive);
                        accountsList.add(savingsAccount);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
return accountsList;
    }

    public void deposit(String accountId, double amount){
        ArrayList<String> updatedLines= new ArrayList<>();
        try {
            List<String> lines= FileManager.readAllLines("src/accounts");
            for (String line:lines){
                String [] data= line.split(",");
                if (accountId.equals(data[0])){
                double balance = Double.parseDouble(data[3]);
                int overdraftCount= Integer.parseInt(data[4]);
                boolean accountActive= Boolean.parseBoolean(data[5]);

                    if (data[2].equals("CHECKING")){
                        CheckingAccount checkingAccount=new CheckingAccount(data[0],balance,overdraftCount,accountActive);
                        checkingAccount.deposit(amount);
                        String updateLine= data[0]+","+data[1]+","+data[2]+","+checkingAccount.getBalance()+","+checkingAccount.getOverdraftCount()+","+checkingAccount.getAccountActive();
                        updatedLines.add(updateLine);
                    }else if(data[2].equals("SAVINGS")){
                        SavingsAccount savingsAccount=new SavingsAccount(data[0],balance,overdraftCount,accountActive);
                        savingsAccount.deposit(amount);
                        String updateLine= data[0]+","+data[1]+","+data[2]+","+savingsAccount.getBalance()+","+savingsAccount.getOverdraftCount()+","+savingsAccount.getAccountActive();
                        updatedLines.add(updateLine);
                    }
                } else {
                    updatedLines.add(line);
                }
            }
            FileManager.overWriteFile("src/accounts",updatedLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void withdraw(String accountId, double amount){
        ArrayList<String> updatedLines= new ArrayList<>();
        try {
            List<String> lines= FileManager.readAllLines("src/accounts");
            for(String line:lines){
                String [] data= line.split(",");
                if (accountId.equals(data[0])){
                    double balance= Double.parseDouble(data[3]);
                    int overdraftCount= Integer.parseInt(data[4]);
                    boolean accountActive= Boolean.parseBoolean(data[5]);
                    if (data[2].equals("CHECKING")){
                        CheckingAccount checkingAccount=new CheckingAccount(data[0],balance,overdraftCount,accountActive);
                        checkingAccount.withdraw(amount);
                        String updateLine= data[0]+","+data[1]+","+data[2]+","+checkingAccount.getBalance()+","+checkingAccount.getOverdraftCount()+","+checkingAccount.getAccountActive();
                        updatedLines.add(updateLine);
                    }else if (data[2].equals("SAVINGS")){
                        SavingsAccount savingsAccount=new SavingsAccount(data[0],balance,overdraftCount,accountActive);
                        savingsAccount.withdraw(amount);
                        String updateLine= data[0]+","+data[1]+","+data[2]+","+savingsAccount.getBalance()+","+savingsAccount.getOverdraftCount()+","+savingsAccount.getAccountActive();
                        updatedLines.add(updateLine);
                    }
                }
                else{
                    updatedLines.add(line);
                }
            }
            FileManager.overWriteFile("src/accounts",updatedLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
