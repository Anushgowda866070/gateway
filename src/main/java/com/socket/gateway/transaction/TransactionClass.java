package com.socket.gateway.transaction;

import com.socket.gateway.dto.SalesRequestDTO;
import com.socket.gateway.socket.GatewayClient;
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

            for (ConstraintViolation<SalesRequestDTO> error : errors) {
                System.out.println(error.getMessage());
            }
            return "Validation Failed";

        }
        return gatewayClient.sendRequest(dto);
    }
}
