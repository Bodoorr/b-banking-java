package models;

public class CheckingAccount extends Account{

    public CheckingAccount(String accountId, double balance, int overdraftCount, boolean accountActive){
        super(accountId,balance,overdraftCount,accountActive);
    }
}
