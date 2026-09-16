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
                double todayDeposits= transactionService.getTodayDepositTotal(accountId);
                double totalTodayAmountDeposit= amount+todayDeposits;
                    if (data[2].equals("CHECKING")){
                        CheckingAccount checkingAccount=new CheckingAccount(data[0],balance,overdraftCount,accountActive,cardType);

                        double checkingOldBalance= checkingAccount.getBalance();
                        if (totalTodayAmountDeposit>checkingAccount.getDebitCard().getDepositLimit()){
                            System.out.println("Deposit exceeds daily limit.");
                            return;
                        }
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

                        if (totalTodayAmountDeposit>savingsAccount.getDebitCard().getDepositLimit()){
                            System.out.println("Deposit exceeds daily limit.");
                            return;
                        }

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
            double todayWithdraws= transactionService.getTodayWithdrawTotal(accountId);
            double totalTodayAmountWithdraw= amount+todayWithdraws;
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

                        if (totalTodayAmountWithdraw>checkingAccount.getDebitCard().getWithdrawLimit()){
                            System.out.println("Withdraw exceeds daily limit.");
                            return;
                        }

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

                        if (totalTodayAmountWithdraw>savingsAccount.getDebitCard().getWithdrawLimit()){
                            System.out.println("Deposit exceeds daily limit.");
                            return;
                        }


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
            boolean ownTransfer= fromCustomerId.equals(toCustomerId);

            String withdrawTransferType;
            if (ownTransfer){
                withdrawTransferType="OWN-TRANSFER-WITHDRAW";
                double todayOwnTransfers= transactionService.getTodayOwnTransferTotal(fromAccountId);
                double totalTodayOwnTransfer = todayOwnTransfers + amount;

                if (totalTodayOwnTransfer > fromAccount.getDebitCard().getOwnTransferLimit()){
                    System.out.println("Your own transfer exceeds daily limit.");
                    return;
                }
            }else {
                withdrawTransferType="TRANSFER-WITHDRAW";
                double todayTransfers= transactionService.getTodayTransferTotal(fromAccountId);
                double totalTodayTransfer = todayTransfers + amount;

                if (totalTodayTransfer > fromAccount.getDebitCard().getTransferLimit()){
                    System.out.println("Your transfer exceeds daily limit.");
                    return;
                }
            }


            String depositTransferType;
            if (ownTransfer){
                depositTransferType="OWN-TRANSFER-DEPOSIT";
                double todayOwnDeposits= transactionService.getTodayOwnTransferDeposit(toAccountId);
                double totalTodayOwnDeposit= todayOwnDeposits+amount;

                if (totalTodayOwnDeposit > toAccount.getDebitCard().getOwnDepositLimit()){
                    System.out.println("Your own deposit exceeds daily limit.");
                    return;
                }

            }else {
                depositTransferType="TRANSFER-DEPOSIT";
                double todayTransferDeposits= transactionService.getTodayTransferDeposit(toAccountId);
                double totalTodayTransferDeposit= todayTransferDeposits+amount;


                if (totalTodayTransferDeposit > toAccount.getDebitCard().getDepositLimit()){
                    System.out.println("Deposit exceeds daily limit.");
                    return;
                }
            }
            double oldBalance= fromAccount.getBalance();

            fromAccount.withdraw(amount);

            if (fromAccount.getBalance() == oldBalance){
                System.out.println("Transfer Failed.");
                return;
            }

            Transaction withdrawTransaction=new Transaction(LocalDateTime.now(),fromCustomerId,fromAccountId,fromAccount.getBalance(),amount,withdrawTransferType);
            transactionService.saveTransaction(withdrawTransaction);
            toAccount.deposit(amount);


            Transaction depositTransaction=new Transaction(LocalDateTime.now(),toCustomerId,toAccountId,toAccount.getBalance(),amount,depositTransferType);
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
