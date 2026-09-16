import models.CheckingAccount;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class AccountTest {
    CheckingAccount account;

    @Before
    public void setAccount(){
        account = new CheckingAccount("A1001", 1000, 0, true, "Platinum");
    }

    @Test
    @DisplayName("Deposit should increase account balance")
    public void depositShouldIncreaseBalance(){
        account.deposit(500);
        Assert.assertEquals(1500.0, account.getBalance(), 0.0);
    }

    @Test
    @DisplayName("Withdraw should decrease account balance")
    public void withdrawShouldDecreaseBalance(){
        account.withdraw(200);
        Assert.assertEquals(800.0, account.getBalance(), 0.0);
    }

    @Test
    @DisplayName("Deposit of zero should not change balance")
    public void zeroDepositShouldNotChangeBalance(){
        account.deposit(0);
        Assert.assertEquals(1000.0, account.getBalance(), 0.0);
    }

    @Test
    @DisplayName("Negative deposit should not change balance")
    public void negativeDepositShouldNotChangeBalance(){
        account.deposit(-100);
        Assert.assertEquals(1000.0, account.getBalance(), 0.0);
    }

    @Test
    @DisplayName("Withdraw of zero should not change balance")
    public void zeroWithdrawShouldNotChangeBalance(){
        account.withdraw(0);
        Assert.assertEquals(1000.0, account.getBalance(), 0.0);
    }

    @Test
    @DisplayName("Negative withdraw should not change balance")
    public void negativeWithdrawShouldNotChangeBalance(){
        account.withdraw(-100);
        Assert.assertEquals(1000.0, account.getBalance(), 0.0);
    }

    @Test
    @DisplayName("Overdraft should charge 35 fee")
    public void overdraftShouldChargeFee(){
        account.withdraw(1100);
        Assert.assertEquals(-135.0, account.getBalance(), 0.0);
        Assert.assertEquals(1, account.getOverdraftCount());
    }

    @Test
    @DisplayName("Two overdrafts should deactivate account")
    public void twoOverdraftsShouldDeactivateAccount(){
        account.withdraw(1100);
        account.withdraw(50);

        Assert.assertEquals(2, account.getOverdraftCount());
        Assert.assertFalse(account.getAccountActive());
    }

    @Test
    @DisplayName("Withdraw over 100 while balance is negative should be declined")
    public void withdrawOver100WhileNegativeShouldBeDeclined(){
        account.withdraw(1100);
        double balanceBefore = account.getBalance();

        account.withdraw(101);

        Assert.assertEquals(balanceBefore, account.getBalance(), 0.0);
    }

    @Test
    @DisplayName("Inactive account should not allow withdrawal")
    public void inactiveAccountShouldNotAllowWithdrawal(){
        account.withdraw(1100);
        account.withdraw(50);

        double balanceBefore = account.getBalance();
        account.withdraw(50);

        Assert.assertEquals(balanceBefore, account.getBalance(), 0.0);
    }

    @Test
    @DisplayName("Resolving negative balance should reactivate account")
    public void resolvingNegativeBalanceShouldReactivateAccount(){
        account.withdraw(1100);
        account.withdraw(50);

        account.deposit(300);

        Assert.assertTrue(account.getAccountActive());
        Assert.assertEquals(0, account.getOverdraftCount());
    }
}
