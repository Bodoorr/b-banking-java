package services;
import models.CheckingAccount;
import models.SavingsAccount;
import models.Transaction;
import models.Account;
import utils.FileManager;

import java.io.IOException;
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

    public void displayAccountStatement(String customerId, Account account){
        System.out.println("DETAILED ACCOUNT STATEMENT\n");
        System.out.println();
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
                if (data[1].equals(customerId) && data[2].equals(account.getAccountId())){
                        String transaction= "Transaction Type: "+data[5]+"\n"+"Date: "+data[0]+"\n"+
                                "\n"+"Amount: "+data[4]+"\n"+"Balance: "+data[3]+"\n";
                        System.out.println(transaction);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
