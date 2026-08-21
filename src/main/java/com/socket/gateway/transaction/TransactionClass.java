package com.socket.gateway.transaction;

import com.socket.gateway.dto.CardDTO;
import com.socket.gateway.dto.RefundRequestDTO;
import com.socket.gateway.dto.SalesRequestDTO;
import com.socket.gateway.dto.Transaction;
import com.socket.gateway.enums.TransactionType;
import com.socket.gateway.schemeresponse.SchemeResponse;
import com.socket.gateway.socket.GatewayClient;
import com.socket.gateway.utility.RandomAlphaNumeric;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import com.socket.gateway.validator.CardValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransactionClass {

    private final GatewayClient gatewayClient;

    private final Map<String, Transaction> transactions=new HashMap<>();

    public TransactionClass(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    public SchemeResponse processSale(SalesRequestDTO dto) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<SalesRequestDTO>> errors =
                validator.validate(dto);

        if (!errors.isEmpty()) {
            StringBuilder message = new StringBuilder();

            for (ConstraintViolation<SalesRequestDTO> error : errors) {
                message.append(error.getMessage()).append("\n");
            }
            SchemeResponse schemeResponse=new SchemeResponse();
            schemeResponse.setResponseMessage(message.toString());
            return schemeResponse;

        }
        if (CardValidator.isCardExpired(dto.getCardEntity().getCardExpiry())){
            SchemeResponse schemeResponse=new SchemeResponse();

            schemeResponse.setResponseMessage("Card Expired");
            return schemeResponse;
        }
        String currencyCode = dto.getMoneyEntity().getCurrencyCode();

        if (!currencyCode.equals("GBP")
                && !currencyCode.equals("EUR")
                && !currencyCode.equals("USD")) {

            SchemeResponse schemeResponse = new SchemeResponse();
            schemeResponse.setResponseMessage("Unsupported Currency");
            return schemeResponse;
        }
        for (Transaction existingTransaction : transactions.values()) {

            if (existingTransaction.getTransactionType() == TransactionType.SALE
                    && existingTransaction.getCardEntity() != null
                    && existingTransaction.getMoneyEntity() != null
                    && existingTransaction.getCardEntity().getCardNumber()
                    .equals(dto.getCardEntity().getCardNumber())
                    && Double.compare(
                    existingTransaction.getMoneyEntity().getAmount(),
                    dto.getMoneyEntity().getAmount()) == 0) {

                SchemeResponse schemeResponse = new SchemeResponse();
                schemeResponse.setResponseMessage("Duplicate Card Transaction");
                return schemeResponse;
            }
        }
        try {
            CardDTO cardDTO = dto.getCardEntity();
            CardValidator.validateCvv(cardDTO.getScheme(), cardDTO.getCvv());
            String transactionId= RandomAlphaNumeric.generateTransactionId(TransactionType.SALE);
            dto.setTransactionId(transactionId);
            dto.setTransactionType(TransactionType.SALE);
            Transaction transaction=new Transaction();
            transaction.setTransactionId(dto.getTransactionId());
            transaction.setMerchantId(dto.getMerchantId());
            transaction.setGatewayReference(dto.getGatewayReference());
            transaction.setRecurrenceFlag(dto.getRecurrenceFlag());
            transaction.setMoneyEntity(dto.getMoneyEntity());
            transaction.setCardEntity(dto.getCardEntity());
            transaction.setAcceptorDetails(dto.getAcceptorDetails());
            transaction.seteWallet(dto.geteWallet());
            transaction.setTransactionType(TransactionType.SALE);
            transactions.put(transaction.getTransactionId(),transaction);
            return gatewayClient.sendRequest(dto);
        } catch (IllegalArgumentException e) {
            SchemeResponse schemeResponse=new SchemeResponse();
            schemeResponse.setResponseMessage(e.getMessage());
            return schemeResponse;
        }
    }

    public SchemeResponse processRefund(RefundRequestDTO dto) {

        Transaction parentTransaction = transactions.get(dto.getParentTransactionId());
        if (parentTransaction == null) {
            SchemeResponse schemeResponse=new SchemeResponse();

            schemeResponse.setResponseMessage("Parent transaction not found");
            return schemeResponse;
        }
        Double saleAmount = parentTransaction.getMoneyEntity().getAmount();
        Double refundAmount = dto.getMoneyEntity().getAmount();

        if (refundAmount > saleAmount) {
            SchemeResponse schemeResponse = new SchemeResponse();
            schemeResponse.setResponseMessage(
                    "Refund amount should not be greater than sale amount"
            );
            return schemeResponse;
        }

        Transaction refundTransaction = new Transaction();

        refundTransaction.setMerchantId(parentTransaction.getMerchantId());
        refundTransaction.setGatewayReference(parentTransaction.getGatewayReference());
        refundTransaction.setRecurrenceFlag(parentTransaction.getRecurrenceFlag());
        refundTransaction.setMoneyEntity(dto.getMoneyEntity());
        refundTransaction.setCardEntity(parentTransaction.getCardEntity());
        refundTransaction.setAcceptorDetails(parentTransaction.getAcceptorDetails());
        refundTransaction.seteWallet(parentTransaction.geteWallet());

        String refundTransactionId =
                RandomAlphaNumeric.generateTransactionId(TransactionType.REFUND);

        refundTransaction.setTransactionId(refundTransactionId);
        refundTransaction.setParentTransactionId(
                parentTransaction.getTransactionId());
        refundTransaction.setTransactionType(TransactionType.REFUND);
        transactions.put(refundTransaction.getTransactionId(),refundTransaction);

        return gatewayClient.sendRequest(refundTransaction);
        }

    public SchemeResponse processVerify(SalesRequestDTO dto) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<SalesRequestDTO>> errors =
                validator.validate(dto);

        if (!errors.isEmpty()) {
            StringBuilder message=new StringBuilder();

            for(ConstraintViolation<SalesRequestDTO>error:errors){
                message.append(error.getMessage()).append("\n");
            }
            SchemeResponse schemeResponse=new SchemeResponse();
            schemeResponse.setResponseMessage(message.toString());
            return schemeResponse;
        }

        String transactionId= RandomAlphaNumeric.generateTransactionId(TransactionType.VERIFY);
        dto.setTransactionId(transactionId);

        return gatewayClient.sendRequest(dto);
    }

    public SchemeResponse processVoid(SalesRequestDTO dto) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<SalesRequestDTO>> errors =
                validator.validate(dto);

        if (!errors.isEmpty()) {
            StringBuilder message=new StringBuilder();

            for(ConstraintViolation<SalesRequestDTO>error:errors){
                message.append(error.getMessage()).append("\n");
            }
            SchemeResponse schemeResponse=new SchemeResponse();
            schemeResponse.setResponseMessage(message.toString());
            return schemeResponse;
        }

        String transactionId= RandomAlphaNumeric.generateTransactionId(TransactionType.VOID);
        dto.setTransactionId(transactionId);

        return gatewayClient.sendRequest(dto);
    }
}
