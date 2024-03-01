package com.User.User.Model;

import lombok.Data;

@Data
public class Address {

    private String country;
    private String state;
    private String city;
    private String locality;
    private long pinCode;

}
