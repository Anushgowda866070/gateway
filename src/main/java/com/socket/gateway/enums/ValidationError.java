package com.socket.gateway.enums;

public enum ValidationError {
    INVALID_CARD_NUMBER("Invalid Card Number"),
    INVALID_CVV("Invalid Cvv "),
    INVALID_AMOUNT("Invalid Amount"),
    INVALID_CURRENCY("Invalid Currency"),
    INVALID_EXPIRY_DATE("Invalid Expiry Date"),
    INVALID_MERCHANT("Invalid Merchant");

     ValidationError(String errorMessage){

    }
}
