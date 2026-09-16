package services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import models.CheckingAccount;
import models.SavingsAccount;
import models.Transaction;
import utils.FileManager;
import models.Account;
public class AccountService {
    TransactionService transactionService = new TransactionService();

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
                    String cardType= data[6];
                    if (data[2].equals("CHECKING")){
                        CheckingAccount checkingAccount=new CheckingAccount(data[0],balance,overdraftCount,accountActive,cardType);
                        accountsList.add(checkingAccount);
                    }else if(data[2].equals("SAVINGS")){
                        SavingsAccount savingsAccount=new SavingsAccount(data[0],balance,overdraftCount,accountActive,cardType);
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
                String cardType= data[6];
                    if (data[2].equals("CHECKING")){
                        CheckingAccount checkingAccount=new CheckingAccount(data[0],balance,overdraftCount,accountActive,cardType);

                        double checkingOldBalance= checkingAccount.getBalance();
                        checkingAccount.deposit(amount);

                        if (checkingAccount.getBalance()==checkingOldBalance){
                            System.out.println("Transaction failed.");
                            return;
                        }

                        String updateLine= data[0]+","+data[1]+","+data[2]+","+checkingAccount.getBalance()+","+checkingAccount.getOverdraftCount()+","+checkingAccount.getAccountActive()+","+data[6];
                        updatedLines.add(updateLine);
                        Transaction transaction=new Transaction(LocalDateTime.now(),data[1],accountId,checkingAccount.getBalance(),amount,"DEPOSIT");
                        transactionService.saveTransaction(transaction);
                    }else if(data[2].equals("SAVINGS")){
                        SavingsAccount savingsAccount=new SavingsAccount(data[0],balance,overdraftCount,accountActive,cardType);

                        double savingsOldBalance= savingsAccount.getBalance();
                        savingsAccount.deposit(amount);

                        if (savingsAccount.getBalance()==savingsOldBalance){
                            System.out.println("Transaction failed.");
                            return;
                        }

                        String updateLine= data[0]+","+data[1]+","+data[2]+","+savingsAccount.getBalance()+","+savingsAccount.getOverdraftCount()+","+savingsAccount.getAccountActive()+","+data[6];
                        updatedLines.add(updateLine);
                        Transaction transaction=new Transaction(LocalDateTime.now(),data[1],accountId,savingsAccount.getBalance(),amount,"DEPOSIT");
                        transactionService.saveTransaction(transaction);
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
                    String cardType= data[6];
                    if (data[2].equals("CHECKING")){
                        CheckingAccount checkingAccount=new CheckingAccount(data[0],balance,overdraftCount,accountActive,cardType);

                        double checkingOldBalance= checkingAccount.getBalance();
                        checkingAccount.withdraw(amount);

                        if (checkingAccount.getBalance() == checkingOldBalance){
                            System.out.println("Transaction failed.");
                            return;
                        }

                        String updateLine= data[0]+","+data[1]+","+data[2]+","+checkingAccount.getBalance()+","+checkingAccount.getOverdraftCount()+","+checkingAccount.getAccountActive()+","+data[6];
                        updatedLines.add(updateLine);
                        Transaction transaction=new Transaction(LocalDateTime.now(),data[1],accountId,checkingAccount.getBalance(),amount,"WITHDRAW");
                        transactionService.saveTransaction(transaction);
                    }else if (data[2].equals("SAVINGS")){
                        SavingsAccount savingsAccount=new SavingsAccount(data[0],balance,overdraftCount,accountActive,cardType);

                        double savingsOldBalance= savingsAccount.getBalance();
                        savingsAccount.withdraw(amount);

                        if (savingsAccount.getBalance()==savingsOldBalance){
                            System.out.println("Transaction failed.");
                            return;
                        }

                        String updateLine= data[0]+","+data[1]+","+data[2]+","+savingsAccount.getBalance()+","+savingsAccount.getOverdraftCount()+","+savingsAccount.getAccountActive()+","+data[6];
                        updatedLines.add(updateLine);
                        Transaction transaction=new Transaction(LocalDateTime.now(),data[1],accountId,savingsAccount.getBalance(),amount,"WITHDRAW");
                        transactionService.saveTransaction(transaction);

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

    public void transfer(String fromAccountId, String toAccountId, double amount){
        Account fromAccount=null;
        Account toAccount=null;

        String fromCustomerId="";
        String toCustomerId="";

        try {
            List<String> lines=FileManager.readAllLines("src/accounts");
            for (String line:lines){
                String [] data=line.split(",");
                double balance= Double.parseDouble(data[3]);
                int overdraftCount= Integer.parseInt(data[4]);
                boolean accountActive= Boolean.parseBoolean(data[5]);
                String cardType= data[6];
                if (data[0].equals(fromAccountId)){
                    fromCustomerId= data[1];
                    if (data[2].equals("CHECKING")){
                        fromAccount= new CheckingAccount(data[0],balance,overdraftCount,accountActive,cardType);
                    } else if (data[2].equals("SAVINGS")) {
                        fromAccount= new SavingsAccount(data[0],balance,overdraftCount,accountActive,cardType);
                    }
                }
                if(data[0].equals(toAccountId)){
                    toCustomerId= data[1];
                    if (data[2].equals("CHECKING")){
                        toAccount=new CheckingAccount(data[0],balance,overdraftCount,accountActive,cardType);
                    }else if (data[2].equals("SAVINGS")){
                        toAccount=new SavingsAccount(data[0],balance,overdraftCount,accountActive,cardType);
                    }
                }
            }
            if (fromAccount==null || toAccount==null){
                System.out.println("No Account Found.");
                return;
            }
            double oldBalance= fromAccount.getBalance();
            fromAccount.withdraw(amount);

            if (fromAccount.getBalance() == oldBalance){
                System.out.println("Transfer Failed.");
                return;
            }
            Transaction withdrawTransaction=new Transaction(LocalDateTime.now(),fromCustomerId,fromAccountId,fromAccount.getBalance(),amount,"TRANSFER-WITHDRAW");
            transactionService.saveTransaction(withdrawTransaction);

            toAccount.deposit(amount);

            Transaction depositTransaction=new Transaction(LocalDateTime.now(),toCustomerId,toAccountId,toAccount.getBalance(),amount,"TRANSFER-DEPOSIT");
            transactionService.saveTransaction(depositTransaction);

            ArrayList<String> updatedLines= new ArrayList<>();
            for (String line:lines){
                String [] data= line.split(",");
                if (data[0].equals(fromAccountId)) {
                    String updateLine= data[0]+","+data[1]+","+data[2]+","+fromAccount.getBalance()+","+fromAccount.getOverdraftCount()+","+fromAccount.getAccountActive()+","+data[6];
                    updatedLines.add(updateLine);
                }else if (data[0].equals(toAccountId)) {
                    String updateLine = data[0] + "," + data[1] + "," + data[2] + "," + toAccount.getBalance() + "," + toAccount.getOverdraftCount() + "," + toAccount.getAccountActive()+","+data[6];
                    updatedLines.add(updateLine);
                }else{
                    updatedLines.add(line);
                }
            }
            FileManager.overWriteFile("src/accounts",updatedLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
