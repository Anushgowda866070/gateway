package com.socket.gateway.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MerchantContactDetailsDTO {

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "state is required")
    private String state;

    @Pattern(regexp = "\\d{10}",
    message = "Number should be 10 Digits")
    @NotBlank(message = "Customer Service Number is required")
    private String customerServiceNumber;

    @Pattern(regexp = "\\d{6}",
    message = "Please enter the valid postal code")
    @NotBlank(message = "Postal Code is required")
    private String postalCode;

    @Email
    @NotBlank(message = "Email Id is required")
    private String emailId;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCustomerServiceNumber() {
        return customerServiceNumber;
    }

    public void setCustomerServiceNumber(String customerServiceNumber) {
        this.customerServiceNumber = customerServiceNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
