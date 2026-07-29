package com.socket.gateway.validator;

import com.socket.gateway.enums.Scheme;

public class CardCvvValidator {


    public static void validate(Scheme scheme,String cvv) {

        if (scheme==Scheme.AMEX) {
            if (!cvv.matches("\\d{4}")) {
                throw new IllegalArgumentException("AMEX CVV must be exactly 4 digits");
            }
        } else {
            if (!cvv.matches("\\d{3}")) {
                throw new IllegalArgumentException("CVV must be exactly 3 digits");
            }
        }
    }
}
