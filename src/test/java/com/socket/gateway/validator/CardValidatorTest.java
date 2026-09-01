package com.socket.gateway.validator;

import com.socket.gateway.enums.Scheme;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardValidatorTest {


    @Test
    void validateCvv_AmexWithFourDigits_ShouldPass() {
        assertDoesNotThrow(() ->
                CardValidator.validateCvv(Scheme.AMEX, "1234")
        );
    }

    @Test
    void validateCvv_AmexWithInvalidCvv_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CardValidator.validateCvv(Scheme.AMEX, "123")
        );

        assertEquals("AMEX CVV must be exactly 4 digits",
                exception.getMessage());
    }

    @Test
    void validateCvv_NonAmexWithThreeDigits_ShouldPass() {
        Scheme nonAmex = Scheme.values()[0] == Scheme.AMEX
                ? Scheme.values()[1]
                : Scheme.values()[0];

        assertDoesNotThrow(() ->
                CardValidator.validateCvv(nonAmex, "123")
        );
    }

    @Test
    void validateCvv_NonAmexWithInvalidCvv_ShouldThrowException() {
        Scheme nonAmex = Scheme.values()[0] == Scheme.AMEX
                ? Scheme.values()[1]
                : Scheme.values()[0];

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CardValidator.validateCvv(nonAmex, "1234")
        );

        assertEquals("CVV must be exactly 3 digits",
                exception.getMessage());
    }


    @Test
    void isCardExpired_NullExpiry_ShouldReturnTrue() {
        assertTrue(CardValidator.isCardExpired(null));
    }

    @Test
    void isCardExpired_BlankExpiry_ShouldReturnTrue() {
        assertTrue(CardValidator.isCardExpired(""));
    }

    @Test
    void isCardExpired_ExpiredCard_ShouldReturnTrue() {
        YearMonth currentMonth =
                YearMonth.now(Clock.systemUTC());

        YearMonth expiredMonth =
                currentMonth.minusMonths(1);

        String expiry = String.format(
                "%02d/%02d",
                expiredMonth.getMonthValue(),
                expiredMonth.getYear() - 2000
        );

        assertTrue(CardValidator.isCardExpired(expiry));
    }

    @Test
    void isCardExpired_CurrentMonth_ShouldReturnFalse() {
        YearMonth currentMonth =
                YearMonth.now(Clock.systemUTC());

        String expiry = String.format(
                "%02d/%02d",
                currentMonth.getMonthValue(),
                currentMonth.getYear() - 2000
        );

        assertFalse(CardValidator.isCardExpired(expiry));
    }

    @Test
    void isCardExpired_FutureCard_ShouldReturnFalse() {
        YearMonth futureMonth =
                YearMonth.now(Clock.systemUTC())
                        .plusMonths(1);

        String expiry = String.format(
                "%02d/%02d",
                futureMonth.getMonthValue(),
                futureMonth.getYear() - 2000
        );

        assertFalse(CardValidator.isCardExpired(expiry));
    }
}