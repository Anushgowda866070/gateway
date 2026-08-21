package com.socket.gateway.utility;

import com.socket.gateway.dto.*;
import com.socket.gateway.model.*;

public class GatewayUtility {
    private GatewayUtility() {
        /* This utility class should not be instantiated */
    }


    public static SalesRequest convertDTOToEntity(SalesRequestDTO salesRequestDTO) {

        SalesRequest salesRequest = new SalesRequest();

        salesRequest.setMerchantId(salesRequestDTO.getMerchantId());
        salesRequest.setGatewayReference(salesRequestDTO.getGatewayReference());
        salesRequest.setRecurrenceFlag(salesRequestDTO.getRecurrenceFlag());
        salesRequest.seteWallet(salesRequestDTO.geteWallet());
        salesRequest.setTransactionType(salesRequestDTO.getTransactionType());

        MoneyEntity money = new MoneyEntity();
        money.setAmount(salesRequestDTO.getMoneyEntity().getAmount());
        money.setCurrencyCode(salesRequestDTO.getMoneyEntity().getCurrencyCode());
        money.setCashback(salesRequestDTO.getMoneyEntity().getCashback());
        salesRequest.setMoneyEntity(money);

        CardEntity card = new CardEntity();
        card.setCardNumber(salesRequestDTO.getCardEntity().getCardNumber());
        card.setCardExpiry(salesRequestDTO.getCardEntity().getCardExpiry());
        card.setCvv(salesRequestDTO.getCardEntity().getCvv());
        salesRequest.setCardEntity(card);


        MerchantContactDetails contact = new MerchantContactDetails();
        contact.setStreet(salesRequestDTO.getAcceptorDetails().getMerchantContactDetails().getStreet());
        contact.setCity(salesRequestDTO.getAcceptorDetails().getMerchantContactDetails().getCity());
        contact.setState(salesRequestDTO.getAcceptorDetails().getMerchantContactDetails().getState());
        contact.setCustomerServiceNumber(salesRequestDTO.getAcceptorDetails().getMerchantContactDetails().getCustomerServiceNumber());
        contact.setPostalCode(salesRequestDTO.getAcceptorDetails().getMerchantContactDetails().getPostalCode());
        contact.setEmailId(salesRequestDTO.getAcceptorDetails().getMerchantContactDetails().getEmailId());

        AcceptorDetails acceptor = new AcceptorDetails();
        acceptor.setMerchantName(salesRequestDTO.getAcceptorDetails().getMerchantName());
        acceptor.setSubMerchantId(salesRequestDTO.getAcceptorDetails().getSubMerchantId());
        acceptor.setMerchantContactDetails(contact);

        salesRequest.setAcceptorDetails(acceptor);

        return salesRequest;
    }

    public static SalesRequestDTO convertEntityToDTO(SalesRequest salesRequest) {

        SalesRequestDTO salesRequestDTO = new SalesRequestDTO();

        salesRequestDTO.setMerchantId(salesRequest.getMerchantId());
        salesRequestDTO.setGatewayReference(salesRequest.getGatewayReference());
        salesRequestDTO.setRecurrenceFlag(salesRequest.getRecurrenceFlag());
        salesRequestDTO.seteWallet(salesRequest.geteWallet());
        salesRequestDTO.setTransactionType(salesRequest.getTransactionType());

        MoneyDTO moneyDTO = new MoneyDTO();
        moneyDTO.setAmount(salesRequest.getMoneyEntity().getAmount());
        moneyDTO.setCurrencyCode(salesRequest.getMoneyEntity().getCurrencyCode());
        moneyDTO.setCashback(salesRequest.getMoneyEntity().getCashback());
        salesRequestDTO.setMoneyEntity(moneyDTO);

        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(salesRequest.getCardEntity().getCardNumber());
        cardDTO.setCardExpiry(salesRequest.getCardEntity().getCardExpiry());
        cardDTO.setCvv(salesRequest.getCardEntity().getCvv());
        salesRequestDTO.setCardEntity(cardDTO);

        MerchantContactDetailsDTO contactDTO = new MerchantContactDetailsDTO();
        contactDTO.setStreet(salesRequest.getAcceptorDetails().getMerchantContactDetails().getStreet());
        contactDTO.setCity(salesRequest.getAcceptorDetails().getMerchantContactDetails().getCity());
        contactDTO.setState(salesRequest.getAcceptorDetails().getMerchantContactDetails().getState());
        contactDTO.setCustomerServiceNumber(salesRequest.getAcceptorDetails().getMerchantContactDetails().getCustomerServiceNumber());
        contactDTO.setPostalCode(salesRequest.getAcceptorDetails().getMerchantContactDetails().getPostalCode());
        contactDTO.setEmailId(salesRequest.getAcceptorDetails().getMerchantContactDetails().getEmailId());

        AcceptorDetailsDTO acceptorDTO = new AcceptorDetailsDTO();
        acceptorDTO.setSubMerchantId((salesRequest.getAcceptorDetails().getSubMerchantId()));
        acceptorDTO.setMerchantName((salesRequest.getAcceptorDetails().getMerchantName()));
        acceptorDTO.setMerchantContactDetails(contactDTO);

        salesRequestDTO.setAcceptorDetails(acceptorDTO);

        return salesRequestDTO;
    }
}