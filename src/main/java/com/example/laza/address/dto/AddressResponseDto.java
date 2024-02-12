package com.example.laza.address.dto;

import com.example.laza.order.dto.OrderResponseDto;
import com.example.laza.order.entity.Order;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")

public class AddressResponseDto {

    private Integer id;
    private String name;
    private String country;
    private String city;
    private String phoneNumber;
    private String address;
}
