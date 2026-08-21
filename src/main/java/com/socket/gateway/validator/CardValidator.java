package com.socket.gateway.validator;

import com.socket.gateway.enums.Scheme;

import java.time.Clock;
import java.time.YearMonth;

public class CardValidator {

    private CardValidator() {
        // Utility class should not be instantiated
    }

    public static void validateCvv(Scheme scheme, String cvv) {

        if (scheme == Scheme.AMEX) {
            if (!cvv.matches("\\d{4}")) {
                throw new IllegalArgumentException(
                        "AMEX CVV must be exactly 4 digits");
            }
        } else {
            if (!cvv.matches("\\d{3}")) {
                throw new IllegalArgumentException(
                        "CVV must be exactly 3 digits");
            }
        }
    }

    public static boolean isCardExpired(String expiry) {

        if (expiry == null || expiry.isBlank()) {
            return true;
        }

        String[] parts = expiry.split("/");

        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]) + 2000;

        YearMonth expiryDate = YearMonth.of(year, month);

        return expiryDate.isBefore(YearMonth.now(Clock.systemUTC()));
    }
}