package com.ecom.model;

import lombok.Data;

@Data
public class OrderRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String paymentMethod;
}
