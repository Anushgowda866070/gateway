package com.socket.gateway.transaction;

import com.socket.gateway.dto.SalesRequestDTO;
import com.socket.gateway.socket.GatewayClient;
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
            StringBuilder message=new StringBuilder();

            for(ConstraintViolation<SalesRequestDTO>error:errors){
                message.append(error.getMessage()).append("\n");
            }
            return message.toString();

        }
        if(CardExpiryValidator.isCardExpired(dto.getCardEntity().getCardExpiry())){
            return "Card Expired";
        }
        return gatewayClient.sendRequest(dto);
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

        return gatewayClient.sendRequest(dto);
    }
}
