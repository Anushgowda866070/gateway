package com.socket.gateway.transaction;

import com.socket.gateway.dto.*;
import com.socket.gateway.enums.TransactionType;
import com.socket.gateway.socket.GatewayClient;
import com.socket.gateway.schemeresponse.SchemeResponse;
import com.socket.gateway.validator.CardValidator;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TransactionClassTest {
    @Mock GatewayClient gatewayClient;
    TransactionClass transactionClass;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        transactionClass = new TransactionClass(gatewayClient);
    }

    private SalesRequestDTO sale(String currency) {
        SalesRequestDTO d = mock(SalesRequestDTO.class);
        CardDTO c = mock(CardDTO.class);
        MoneyDTO m = mock(MoneyDTO.class);
        when(c.getCardNumber()).thenReturn("1234567890123456");
        when(c.getCardExpiry()).thenReturn("12/99");
        when(c.getCvv()).thenReturn("123");
        when(m.getAmount()).thenReturn(100.0);
        when(m.getCurrencyCode()).thenReturn(currency);

        when(d.getCardEntity()).thenReturn(c);
        when(d.getMoneyEntity()).thenReturn(m);
        return d;
    }

    private void validation(SalesRequestDTO d, MockedStatic<Validation> v) {
        ValidatorFactory f = mock(ValidatorFactory.class);
        Validator validator = mock(Validator.class);
        when(f.getValidator()).thenReturn(validator);
        when(validator.validate(d)).thenReturn(Collections.emptySet());
        v.when(Validation::buildDefaultValidatorFactory).thenReturn(f);
    }

    private Map<String, Transaction> map() throws Exception {
        Field f = TransactionClass.class.getDeclaredField("transactions");
        f.setAccessible(true);
        return (Map<String, Transaction>) f.get(transactionClass);
    }

    private void successGateway() {
        SchemeResponse r = new SchemeResponse();
        r.setResponseMessage("SUCCESS");
        when(gatewayClient.sendRequest(any())).thenReturn(r);
    }

    @Test
    void saleValidation() {
        SchemeResponse r = transactionClass.processSale(new SalesRequestDTO());
        assertNotNull(r.getResponseMessage());
    }

    @Test
    void saleExpired() {
        SalesRequestDTO d = sale("USD");

        try (MockedStatic<Validation> v = mockStatic(Validation.class);
             MockedStatic<CardValidator> c = mockStatic(CardValidator.class)) {

            validation(d, v);
            c.when(() -> CardValidator.isCardExpired("12/99")).thenReturn(true);
            assertEquals("Card Expired",
                    transactionClass.processSale(d).getResponseMessage());
        }
    }

    @Test
    void saleUnsupportedCurrency() {
        SalesRequestDTO d = sale("INR");

        try (MockedStatic<Validation> v = mockStatic(Validation.class);
             MockedStatic<CardValidator> c = mockStatic(CardValidator.class)) {
            validation(d, v);
            c.when(() -> CardValidator.isCardExpired("12/99")).thenReturn(false);

            assertEquals("Unsupported Currency", transactionClass.processSale(d).getResponseMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"GBP", "EUR", "USD"})
    void saleSuccess(String currency) {
        SalesRequestDTO d = sale(currency);
        successGateway();

        try (MockedStatic<Validation> v = mockStatic(Validation.class);
             MockedStatic<CardValidator> c = mockStatic(CardValidator.class)) {

            validation(d, v);
            c.when(() -> CardValidator.isCardExpired("12/99")).thenReturn(false);
            c.when(() -> CardValidator.validateCvv(any(), any())).thenAnswer(x -> null);

            assertEquals("SUCCESS", transactionClass.processSale(d).getResponseMessage());
            verify(gatewayClient).sendRequest(d);
        }
    }

    @Test
    void saleCvvException() {
        SalesRequestDTO d = sale("USD");
        try (MockedStatic<Validation> v = mockStatic(Validation.class);
             MockedStatic<CardValidator> c = mockStatic(CardValidator.class)) {

            validation(d, v);
            c.when(() -> CardValidator.isCardExpired("12/99")).thenReturn(false);
            c.when(() -> CardValidator.validateCvv(any(), any())).thenThrow(new IllegalArgumentException("Invalid CVV"));
            assertEquals("Invalid CVV", transactionClass.processSale(d).getResponseMessage());
        }
    }

    @Test
    void duplicateSale() throws Exception {
        SalesRequestDTO d = sale("USD");
        CardDTO card = d.getCardEntity();
        MoneyDTO money = d.getMoneyEntity();
        Transaction t = mock(Transaction.class);
        when(t.getTransactionType()).thenReturn(TransactionType.SALE);
        when(t.getCardEntity()).thenReturn(card);
        when(t.getMoneyEntity()).thenReturn(money);
        map().put("T1", t);
        try (MockedStatic<Validation> v = mockStatic(Validation.class);
             MockedStatic<CardValidator> c = mockStatic(CardValidator.class)) {
            validation(d, v);
            c.when(() -> CardValidator.isCardExpired("12/99")).thenReturn(false);
            SchemeResponse response = transactionClass.processSale(d);
            assertEquals("Duplicate Card Transaction", response.getResponseMessage());
            verify(gatewayClient,never()).sendRequest (any());
        }
    }

    @Test
    void refundParentMissing() {
        RefundRequestDTO d = new RefundRequestDTO();
        d.setParentTransactionId("BAD");
        assertEquals("Parent transaction not found", transactionClass.processRefund(d).getResponseMessage());
    }

    @Test
    void refundGreater() throws Exception {
        String id = "SALE1";
        Transaction t = mock(Transaction.class);
        MoneyDTO m = new MoneyDTO();
        m.setAmount(100.0);
        when(t.getMoneyEntity()).thenReturn(m);
        map().put(id, t);
        RefundRequestDTO d = new RefundRequestDTO();
        d.setParentTransactionId(id);
        MoneyDTO rm = new MoneyDTO();
        rm.setAmount(150.0);
        d.setMoneyEntity(rm);
        assertEquals("Refund amount should not be greater than sale amount", transactionClass.processRefund(d).getResponseMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {50.0, 100.0})
    void refundSuccess(double amount) throws Exception {
        String id = "SALE1";
        Transaction t = mock(Transaction.class);
        MoneyDTO m = new MoneyDTO();
        m.setAmount(100.0);
        when(t.getMoneyEntity()).thenReturn(m);
        when(t.getTransactionId()).thenReturn(id);
        map().put(id, t);
        RefundRequestDTO d = new RefundRequestDTO();
        d.setParentTransactionId(id);
        MoneyDTO rm = new MoneyDTO();
        rm.setAmount(amount);
        d.setMoneyEntity(rm);
        successGateway();
        assertEquals("SUCCESS", transactionClass.processRefund(d).getResponseMessage());
    }

    @Test
    void verifyValidation() {
        assertNotNull(transactionClass.processVerify(new SalesRequestDTO()));
    }

    @Test
    void verifySuccess() {
        SalesRequestDTO d = mock(SalesRequestDTO.class);
        successGateway();
        try (MockedStatic<Validation> v = mockStatic(Validation.class)) {
            validation(d, v);
            assertEquals("SUCCESS", transactionClass.processVerify(d).getResponseMessage());
            verify(gatewayClient).sendRequest(d);
        }
    }

    @Test
    void voidValidation() {
        assertNotNull(transactionClass.processVoid(new SalesRequestDTO()));
    }

    @Test
    void voidSuccess() {
        SalesRequestDTO d = mock(SalesRequestDTO.class);
        successGateway();
        try (MockedStatic<Validation> v = mockStatic(Validation.class)) {
            validation(d, v);
            assertEquals("SUCCESS", transactionClass.processVoid(d).getResponseMessage());
            verify(gatewayClient).sendRequest(d);
        }
    }
}