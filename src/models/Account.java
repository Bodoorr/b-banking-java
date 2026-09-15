package models;

public abstract class Account {
    private double balance;
    private String accountId;
    private int overdraftCount;
    private boolean accountActive;

    public Account(String accountId, double balance, int overdraftCount, boolean accountActive){
        this.accountId= accountId;
        this.balance= balance;
        this.overdraftCount= overdraftCount;
        this.accountActive= accountActive;
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

        if (balance>=0 && !accountActive){
            accountActive=true;
            overdraftCount=0;
        }
    }

    //withdraw
    public void withdraw(double amount){
        if (!accountActive){
            System.out.println("Account is deactivated.");
            return;
        }

        if (balance<0 && amount>100){
            System.out.println("Decline, You can't make this request.");
            return;
        }
        balance -= amount;

        if (balance<0){
            balance-=35;
            overdraftCount++;

            if (overdraftCount>=2){
                accountActive= false;
            }
        }

    }

    public int getOverdraftCount(){
        return overdraftCount;
    }

    public boolean getAccountActive(){
        return accountActive;
    }


}
