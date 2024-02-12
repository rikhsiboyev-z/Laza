package com.example.laza.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressCreateDto {

    private String name;
    private String country;
    private String city;
    private String phoneNumber;
    private String address;
}
