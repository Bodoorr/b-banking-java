import models.DebitCard;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import utils.PasswordUtils;
import java.security.NoSuchAlgorithmException;

public class PasswordUtilsTest {
    @Test
    @DisplayName("Hashed password should not equal original password")
    public void hashedPasswordShouldNotEqualOriginalPassword() throws NoSuchAlgorithmException {
        String password = "12345678";
        String hashedPassword = PasswordUtils.hashPassword(password);

        Assert.assertNotEquals(password, hashedPassword);
    }

    @Test
    @DisplayName("Same password should generate same hash")
    public void samePasswordShouldGenerateSameHash() throws NoSuchAlgorithmException {
        String firstHash = PasswordUtils.hashPassword("12345678");
        String secondHash = PasswordUtils.hashPassword("12345678");

        Assert.assertEquals(firstHash, secondHash);
    }

    @Test
    @DisplayName("Different passwords should generate different hashes")
    public void differentPasswordsShouldGenerateDifferentHashes() throws NoSuchAlgorithmException {
        String firstHash = PasswordUtils.hashPassword("12345678");
        String secondHash = PasswordUtils.hashPassword("87654321");

        Assert.assertNotEquals(firstHash, secondHash);
    }
}
