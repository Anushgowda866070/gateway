package com.socket.gateway.validator;

import java.time.YearMonth;

public class CardExpiryValidator {
    public static boolean isCardExpired(String expiry){
        if (expiry==null || expiry.isBlank()){
            return true;
        }
        String[] parts=expiry.split("/");

        int month=Integer.parseInt(parts[0]);
        int year=Integer.parseInt(parts[1])+2000;

        YearMonth expiryDate=YearMonth.of(year,month);

        return expiryDate.isBefore(YearMonth.now());
    }
}
