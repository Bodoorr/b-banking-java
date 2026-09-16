import models.DebitCard;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import services.CustomerService;
public class CustomerServiceTest {
    CustomerService customerService;

    @Before
    public void setCustomerService(){
        customerService = new CustomerService();
    }

    @Test
    @DisplayName("Generated customer ID should start with C")
    public void customerIdShouldStartWithC(){
        String customerId = customerService.generateCustomerId();

        Assert.assertTrue(customerId.startsWith("C"));
    }

    @Test
    @DisplayName("Generated account ID should start with A")
    public void accountIdShouldStartWithA(){
        String accountId = customerService.generateAccountId();

        Assert.assertTrue(accountId.startsWith("A"));
    }

    @Test
    @DisplayName("Generated customer ID should have 5 characters")
    public void customerIdShouldHaveCorrectLength(){
        String customerId = customerService.generateCustomerId();

        Assert.assertEquals(5, customerId.length());
    }

    @Test
    @DisplayName("Generated account ID should have 5 characters")
    public void accountIdShouldHaveCorrectLength(){
        String accountId = customerService.generateAccountId();

        Assert.assertEquals(5, accountId.length());
    }
}
