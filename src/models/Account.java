package models;

public abstract class Account {
    private double balance;
    private String accountId;


    public Account(String accountId, double balance){
        this.accountId= accountId;
        this.balance= balance;
    }

    public String getAccountId(){
        return accountId;
    }

    public double getBalance(){
        return balance;
    }

    //deposit
    public void deposit(double amount){
        balance += amount;
    }

    //withdraw
    public void withdraw(double amount){
        balance -= amount;
    }

}
