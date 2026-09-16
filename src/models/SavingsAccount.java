package models;

public class SavingsAccount extends Account{

    public SavingsAccount(String accountId, double balance, int overdraftCount, boolean accountActive, String cardType){
        super(accountId,balance,overdraftCount,accountActive,cardType);
    }
}
