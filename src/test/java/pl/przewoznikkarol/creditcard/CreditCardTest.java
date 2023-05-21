package pl.przewoznikkarol.creditcard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class CreditCardTest {
    @Test
    void itAllowsToAssignCreditLimit() {
        //Arrange
        CreditCard card = new CreditCard("1234-4567");
        //Act
        card.assignLimit(BigDecimal.valueOf(1000));
        //Assert
        assertEquals(BigDecimal.valueOf(1000), card.getBalance());
    }

    @Test
    void itAllowsToAssignDifferentCreditLimit() {
        //Arrange
        CreditCard card1 = new CreditCard("1234-4567");
        CreditCard card2 = new CreditCard("1234-4567");
        //Act
        card1.assignLimit(BigDecimal.valueOf(1000));
        card2.assignLimit(BigDecimal.valueOf(1100));
        //Assert
        assertEquals(BigDecimal.valueOf(1000), card1.getBalance());
        assertEquals(BigDecimal.valueOf(1100), card2.getBalance());
    }

    @Test
    void itCantAssignLimitBelowCertainThreshold(){
        CreditCard card1 = new CreditCard("1234-4567");

        try{
            card1.assignLimit(BigDecimal.valueOf(10));
            fail("Should throw exception");
        } catch (CreditLimitBelowException e) {
            assertTrue(true);
        }

        assertThrows(CreditLimitBelowException.class, () -> card1.assignLimit(BigDecimal.valueOf(10)));
        assertThrows(CreditLimitBelowException.class, () -> card1.assignLimit(BigDecimal.valueOf(99)));
        assertDoesNotThrow(() -> card1.assignLimit(BigDecimal.valueOf(100)));

    }
    @Test
    void itDoesntAllowSetLimitTwice(){
        CreditCard card = new CreditCard("1234-5678");
        card.assignLimit(BigDecimal.valueOf(110));
        assertThrows(LimitAlreadyAssignedException.class, () -> card.assignLimit(BigDecimal.valueOf(10)));
    }

    @Test
    void allowsToWithdraw(){
        CreditCard card = new CreditCard("1234-4567");
        card.assignLimit(BigDecimal.valueOf(110));
        assertDoesNotThrow(() -> card.withdraw(BigDecimal.valueOf(100)));
    }
    @Test
    void cantWithdrawOverLimit(){
    CreditCard card = new CreditCard("1234-5678");
    card.assignLimit(BigDecimal.valueOf(110));
    assertThrows(WithdrawBiggerThanLimitException.class, () -> card.withdraw(BigDecimal.valueOf(120)));
    }

    @Test
    void cantWithdrawOverBalance(){
        CreditCard card = new CreditCard("1234-5678");
        card.assignLimit(BigDecimal.valueOf(110));
        card.withdraw(BigDecimal.valueOf(50));
        assertThrows(WithdrawBiggerThanBalanceException.class, () -> card.withdraw(BigDecimal.valueOf(90)));
    }

    @Test
    void cantWithdrawMoreThanTenTimes(){
        CreditCard card = new CreditCard("1234-5678");
        card.assignLimit(BigDecimal.valueOf(100));
        for(int i=0;i<10;i++){
            card.withdraw(BigDecimal.valueOf(1));
        }
        assertThrows(WithdrawCounterException.class, () -> card.withdraw(BigDecimal.valueOf(1)));
    }
}
