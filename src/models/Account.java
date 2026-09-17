package models;

public abstract class Account {
    private double balance;
    private String accountId;
    private int overdraftCount;
    private boolean accountActive;
    private DebitCard debitCard;


    public Account(String accountId, double balance, int overdraftCount, boolean accountActive, String cardType){
        this.accountId= accountId;
        this.balance= balance;
        this.overdraftCount= overdraftCount;
        this.accountActive= accountActive;

        debitCard=new DebitCard(cardType);
    }

    public String getAccountId(){
        return accountId;
    }

    public double getBalance(){
        return balance;
    }

    public void deposit(double amount){
        if (amount<=0){
            System.out.println("Amount must be greater than 0.");
            return;
    }
        balance += amount;

        if (balance>=0 && !accountActive){
            accountActive=true;
            overdraftCount=0;
        }
    }

    public void withdraw(double amount){

        if (amount<=0){
            System.out.println("Amount must be greater than 0.");
            return;
        }
        if (!accountActive){
            System.out.println("Account is deactivated.");
            return;
        }

        if (balance<0 && amount>100){
            System.out.println("Declined. You can't make this request.");
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

    public DebitCard getDebitCard(){
        return debitCard;
    }
}
