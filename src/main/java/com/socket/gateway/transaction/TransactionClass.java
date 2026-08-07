package com.socket.gateway.transaction;

import com.socket.gateway.dto.CardDTO;
import com.socket.gateway.dto.SalesRequestDTO;
import com.socket.gateway.enums.TransactionType;
import com.socket.gateway.socket.GatewayClient;
import com.socket.gateway.utility.RandomAlphaNumeric;
import com.socket.gateway.validator.CardCvvValidator;
import com.socket.gateway.validator.CardExpiryValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class TransactionClass {

    private final GatewayClient gatewayClient;

    public TransactionClass(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    public String processSale(SalesRequestDTO dto) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<SalesRequestDTO>> errors =
                validator.validate(dto);

        if (!errors.isEmpty()) {
            StringBuilder message = new StringBuilder();

            for (ConstraintViolation<SalesRequestDTO> error : errors) {
                message.append(error.getMessage()).append("\n");
            }
            return message.toString();

        }
        if (CardExpiryValidator.isCardExpired(dto.getCardEntity().getCardExpiry())) {
            return "Card Expired";
        }
        try {
            CardDTO cardDTO = dto.getCardEntity();
            CardCvvValidator.validate(cardDTO.getScheme(), cardDTO.getCvv());
            String transactionId= RandomAlphaNumeric.generateTransactionId(TransactionType.SALE);
            dto.setTransactionId(transactionId);
            dto.setTransactionType(TransactionType.SALE);
            return gatewayClient.sendRequest(dto);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String processRefund(SalesRequestDTO dto) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<SalesRequestDTO>> errors =
                validator.validate(dto);

        if (!errors.isEmpty()) {
            StringBuilder message=new StringBuilder();

            for(ConstraintViolation<SalesRequestDTO>error:errors){
                message.append(error.getMessage()).append("\n");
            }
            return message.toString();
        }
        if(dto.getParentTransactionId()==null || dto.getParentTransactionId().isBlank()) {
            String transactionId = RandomAlphaNumeric.generateTransactionId(TransactionType.REFUND);
            dto.setTransactionId(transactionId);
            dto.setTransactionType(TransactionType.REFUND);
        }

        return gatewayClient.sendRequest(dto);
    }

    public String processVerify(SalesRequestDTO dto) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<SalesRequestDTO>> errors =
                validator.validate(dto);

        if (!errors.isEmpty()) {
            StringBuilder message=new StringBuilder();

            for(ConstraintViolation<SalesRequestDTO>error:errors){
                message.append(error.getMessage()).append("\n");
            }
            return message.toString();
        }

        String transactionId= RandomAlphaNumeric.generateTransactionId(TransactionType.VERIFY);
        dto.setTransactionId(transactionId);

        return gatewayClient.sendRequest(dto);
    }

    public String processVoid(SalesRequestDTO dto) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<SalesRequestDTO>> errors =
                validator.validate(dto);

        if (!errors.isEmpty()) {
            StringBuilder message=new StringBuilder();

            for(ConstraintViolation<SalesRequestDTO>error:errors){
                message.append(error.getMessage()).append("\n");
            }
            return message.toString();
        }

        String transactionId= RandomAlphaNumeric.generateTransactionId(TransactionType.VOID);
        dto.setTransactionId(transactionId);

        return gatewayClient.sendRequest(dto);
    }
}
