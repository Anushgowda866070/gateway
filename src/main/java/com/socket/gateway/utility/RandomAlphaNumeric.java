package com.socket.gateway.utility;

import com.socket.gateway.enums.TransactionType;
import java.util.logging.Logger;
import java.security.SecureRandom;

public class RandomAlphaNumeric {

    private static final Logger LOGGER=Logger.getLogger(RandomAlphaNumeric.class.getName());
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }
    public static String generateTransactionId(TransactionType transactionType) {

        String prefix;

        switch (transactionType) {
            case SALE:
                prefix = "01S";
                break;

            case REFUND:
                prefix = "01R";
                break;

            case VERIFY:
                prefix = "01V";
                break;

            case VOID:
                prefix = "01D";
                break;

            default:
                throw new IllegalArgumentException("Invalid Transaction Type");
        }

        return prefix + generateRandomString();
    }

    public static void main(String[] args) {
        String randomValue = generateRandomString();

        LOGGER.info(randomValue);
    }
}
