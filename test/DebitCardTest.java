import models.DebitCard;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class DebitCardTest {

        DebitCard platinumCard;
        DebitCard titaniumCard;
        DebitCard mastercard;

        @Before
        public void setDebitCards() {
            platinumCard = new DebitCard("Platinum");
            titaniumCard = new DebitCard("Titanium");
            mastercard = new DebitCard("Mastercard");
        }

        @Test
        @DisplayName("Platinum card should have 20000 withdraw limit")
        public void platinumCardShouldHaveCorrectWithdrawLimit() {
            Assert.assertEquals(20000.0, platinumCard.getWithdrawLimit(), 0.0);
        }

        @Test
        @DisplayName("Platinum card should have 40000 transfer limit")
        public void platinumCardShouldHaveCorrectTransferLimit() {
            Assert.assertEquals(40000.0, platinumCard.getTransferLimit(), 0.0);
        }

        @Test
        @DisplayName("Platinum card should have 80000 own transfer limit")
        public void platinumCardShouldHaveCorrectOwnTransferLimit() {
            Assert.assertEquals(80000.0, platinumCard.getOwnTransferLimit(), 0.0);
        }

        @Test
        @DisplayName("Platinum card should have 100000 deposit limit")
        public void platinumCardShouldHaveCorrectDepositLimit() {
            Assert.assertEquals(100000.0, platinumCard.getDepositLimit(), 0.0);
        }

        @Test
        @DisplayName("Platinum card should have 200000 own deposit limit")
        public void platinumCardShouldHaveCorrectOwnDepositLimit() {
            Assert.assertEquals(200000.0, platinumCard.getOwnDepositLimit(), 0.0);
        }

        @Test
        @DisplayName("Titanium card should have 10000 withdraw limit")
        public void titaniumCardShouldHaveCorrectWithdrawLimit() {
            Assert.assertEquals(10000.0, titaniumCard.getWithdrawLimit(), 0.0);
        }

        @Test
        @DisplayName("Titanium card should have 20000 transfer limit")
        public void titaniumCardShouldHaveCorrectTransferLimit() {
            Assert.assertEquals(20000.0, titaniumCard.getTransferLimit(), 0.0);
        }

        @Test
        @DisplayName("Titanium card should have 40000 own transfer limit")
        public void titaniumCardShouldHaveCorrectOwnTransferLimit() {
            Assert.assertEquals(40000.0, titaniumCard.getOwnTransferLimit(), 0.0);
        }

        @Test
        @DisplayName("Titanium card should have 100000 deposit limit")
        public void titaniumCardShouldHaveCorrectDepositLimit() {
            Assert.assertEquals(100000.0, titaniumCard.getDepositLimit(), 0.0);
        }

        @Test
        @DisplayName("Titanium card should have 200000 own deposit limit")
        public void titaniumCardShouldHaveCorrectOwnDepositLimit() {
            Assert.assertEquals(200000.0, titaniumCard.getOwnDepositLimit(), 0.0);
        }

        @Test
        @DisplayName("Mastercard should have 5000 withdraw limit")
        public void mastercardShouldHaveCorrectWithdrawLimit() {
            Assert.assertEquals(5000.0, mastercard.getWithdrawLimit(), 0.0);
        }

        @Test
        @DisplayName("Mastercard should have 10000 transfer limit")
        public void mastercardShouldHaveCorrectTransferLimit() {
            Assert.assertEquals(10000.0, mastercard.getTransferLimit(), 0.0);
        }

        @Test
        @DisplayName("Mastercard should have 20000 own transfer limit")
        public void mastercardShouldHaveCorrectOwnTransferLimit() {
            Assert.assertEquals(20000.0, mastercard.getOwnTransferLimit(), 0.0);
        }

        @Test
        @DisplayName("Mastercard should have 100000 deposit limit")
        public void mastercardShouldHaveCorrectDepositLimit() {
            Assert.assertEquals(100000.0, mastercard.getDepositLimit(), 0.0);
        }

        @Test
        @DisplayName("Mastercard should have 200000 own deposit limit")
        public void mastercardShouldHaveCorrectOwnDepositLimit() {
            Assert.assertEquals(200000.0, mastercard.getOwnDepositLimit(), 0.0);
        }

    }


