package models;

import java.time.LocalDateTime;

public class Transaction{
    private String customerId;
    private String accountId;
    private double balance;
    private double amount;
    private String transactionType;
    private LocalDateTime date;

    public Transaction(LocalDateTime date, String customerId, String accountId, double balance, double amount, String transactionType){
        this.customerId= customerId;
        this.accountId= accountId;
        this.balance= balance;
        this.amount= amount;
        this.transactionType= transactionType;
        this.date= date;
    }

    public String getCustomerId(){
        return customerId;
    }

    public String getAccountId(){
        return accountId;
    }

    public double getBalance(){
        return balance;
    }

    public double getAmount(){
        return amount;
    }

    public String getTransactionType(){
        return transactionType;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

