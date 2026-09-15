package services;
import models.Transaction;
import utils.FileManager;

import java.util.ArrayList;

public class TransactionService {
    public void saveTransaction(Transaction transaction){
        String transactionData= transaction.getDate()+","+transaction.getCustomerId()+","+transaction.getAccountId()+","+transaction.getBalance()+","+transaction.getAmount()+","+transaction.getTransactionType();

        ArrayList<String> transactions=new ArrayList<>();
        transactions.add(transactionData);
        FileManager.writeData("src/transactions",transactions);
    }
}
