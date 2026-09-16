package services;
import models.CheckingAccount;
import models.SavingsAccount;
import models.Transaction;
import models.Account;
import models.User;
import utils.FileManager;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    public void saveTransaction(Transaction transaction){
        String transactionData= transaction.getDate()+","+transaction.getCustomerId()+","+transaction.getAccountId()+","+transaction.getBalance()+","+transaction.getAmount()+","+transaction.getTransactionType();

        ArrayList<String> transactions=new ArrayList<>();
        transactions.add(transactionData);
        FileManager.writeData("src/transactions",transactions);
    }


    public void displayTransaction(String customerId){
        try {
            List<String> lines= FileManager.readAllLines("src/transactions");
            int transactionCount=0;
            for (String line:lines){
                String [] data=line.split(",");
                if (data[1].equals(customerId)){
                    transactionCount++;
                    String transaction= "Transaction Type: "+data[5]+"\n"+"Date: "+data[0]+"\n"+"Account: "+data[2]+
                            "\n"+"Amount: "+data[4]+"\n"+"Balance: "+data[3]+"\n";
                    System.out.println(transaction);
                }
            }
            if (transactionCount==0){
                System.out.println("No transactions found.");
            }else {
                System.out.println("Total Transactions: "+transactionCount);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayAccountStatement(User user, Account account){
        System.out.println("DETAILED ACCOUNT STATEMENT\n");
        System.out.println("Customer: "+user.getFirstName()+" "+user.getLastName());
        if (account instanceof CheckingAccount){
            System.out.println("Account Type: Checking");
        } else if (account instanceof SavingsAccount){
            System.out.println("Account Type: Savings");
        }
        System.out.println("Account Number: "+account.getAccountId());
        System.out.println("Current Balance: "+account.getBalance()+" BHD");
        System.out.println();
        System.out.println("TRANSACTIONS");
        System.out.println();

        //read transactions
        try {
            List<String> lines= FileManager.readAllLines("src/transactions");

            for (String line:lines){
                String [] data= line.split(",");
                if (data[1].equals(user.getId()) && data[2].equals(account.getAccountId())){
                        String transaction= "Transaction Type: "+data[5]+"\n"+"Date: "+data[0]+
                                "\n"+"Amount: "+data[4]+"\n"+"Balance: "+data[3]+"\n";
                        System.out.println(transaction);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDate getTransactionDate(String [] data){
        LocalDateTime transactionDateTime= LocalDateTime.parse(data[0]);
        return transactionDateTime.toLocalDate();
    }

    public void filterTransactions(User user, String filter){
        LocalDate today= LocalDate.now();
        LocalDate yesterday= today.minusDays(1);

        int monthCount= today.getDayOfMonth();
        LocalDate firstDate= today.minusDays(monthCount-1);
        LocalDate lastMonth= firstDate.minusMonths(1);
        LocalDate endDate= firstDate.minusDays(1);


        int dayCount= today.getDayOfWeek().getValue();
        LocalDate startOfThisWeek= today.minusDays(dayCount-1);
        LocalDate lastWeek= startOfThisWeek.minusWeeks(1);
        LocalDate endOfLastWeek= lastWeek.plusDays(6);


        LocalDate last7days= today.minusDays(6);
        LocalDate last30Days= today.minusDays(29);

        try {
            List<String> lines=FileManager.readAllLines("src/transactions");

           List<String[]> filteredTransactions = lines.stream().map(line-> line.split(","))
                   .filter(data-> data[1].equals(user.getId()))
                   .filter(data-> {
                       LocalDate transactionDate= getTransactionDate(data);
                       if (filter.equals("today")){
                           return transactionDate.equals(today);
                       }
                       if (filter.equals("yesterday")){
                           return transactionDate.equals(yesterday);
                       }
                       if (filter.equals("lastMonth")){
                          return !transactionDate.isBefore(lastMonth) && !transactionDate.isAfter(endDate);
                       }
                       if (filter.equals("lastWeek")){
                           return  !transactionDate.isBefore(lastWeek) && !transactionDate.isAfter(endOfLastWeek);
                       }

                       if (filter.equals("last7days")){
                           return !transactionDate.isBefore(last7days) && !transactionDate.isAfter(today);
                       }

                       if (filter.equals("last30days")){
                           return !transactionDate.isBefore(last30Days) && !transactionDate.isAfter(today);
                       }
                       return false;
                           }).toList();

                   filteredTransactions.forEach(data-> {
                       System.out.println("Transaction Type: "+data[5]);
                       System.out.println("Date: "+data[0]);
                       System.out.println("Amount: "+data[4]);
                       System.out.println("Balance: "+data[3]);
                       System.out.println();
                   });
            if (filteredTransactions.isEmpty()){
                System.out.println("No transactions found.");
            }else {
                System.out.println("Total Transactions: "+filteredTransactions.size());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getTodayDepositTotal(String accountId){
        double total= 0;
        try {
            List<String> lines=FileManager.readAllLines("src/transactions");
            LocalDate today=LocalDate.now();
            for (String line:lines){
                String [] data=line.split(",");
                double amount= Double.parseDouble(data[4]);
                if (data[2].equals(accountId) && data[5].equals("DEPOSIT") && getTransactionDate(data).equals(today)){
                    total+=amount;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return total;
    }


    public double getTodayWithdrawTotal(String accountId){
        double total= 0;
        try {
            List<String> lines=FileManager.readAllLines("src/transactions");
            LocalDate today=LocalDate.now();
            for (String line:lines){
                String [] data=line.split(",");
                double amount= Double.parseDouble(data[4]);
                if (data[2].equals(accountId) && data[5].equals("WITHDRAW") && getTransactionDate(data).equals(today)){
                    total+=amount;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return total;
    }


    public double getTodayOwnTransferTotal(String accountId){
        double total= 0;
        try {
            List<String> lines=FileManager.readAllLines("src/transactions");
            LocalDate today=LocalDate.now();
            for (String line:lines){
                String [] data=line.split(",");
                double amount= Double.parseDouble(data[4]);

                if (data[2].equals(accountId) && data[5].equals("OWN-TRANSFER-WITHDRAW") && getTransactionDate(data).equals(today)){
                    total+=amount;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return total;
    }

    public double getTodayTransferTotal(String accountId){
        double total= 0;
        try {
            List<String> lines=FileManager.readAllLines("src/transactions");
            LocalDate today=LocalDate.now();
            for (String line:lines){
                String [] data=line.split(",");
                double amount= Double.parseDouble(data[4]);

                if (data[2].equals(accountId) && data[5].equals("TRANSFER-WITHDRAW") && getTransactionDate(data).equals(today)){
                    total+=amount;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return total;
    }

    public double getTodayOwnTransferDeposit(String accountId){
        double total= 0;
        try {
            List<String> lines=FileManager.readAllLines("src/transactions");
            LocalDate today=LocalDate.now();
            for (String line:lines){
                String [] data=line.split(",");
                double amount= Double.parseDouble(data[4]);

                if (data[2].equals(accountId) && data[5].equals("OWN-TRANSFER-DEPOSIT") && getTransactionDate(data).equals(today)){
                    total+=amount;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return total;
    }

    public double getTodayTransferDeposit(String accountId){
        double total= 0;
        try {
            List<String> lines=FileManager.readAllLines("src/transactions");
            LocalDate today=LocalDate.now();
            for (String line:lines){
                String [] data=line.split(",");
                double amount= Double.parseDouble(data[4]);

                if (data[2].equals(accountId) && data[5].equals("TRANSFER-DEPOSIT") && getTransactionDate(data).equals(today)){
                    total+=amount;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return total;
    }


}
