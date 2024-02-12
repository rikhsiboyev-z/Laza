package com.example.laza.order.dto;

import com.example.laza.address.dto.AddressResponseDto;
import com.example.laza.address.entity.Address;
import com.example.laza.category.dto.CategoryResponseDto;
import com.example.laza.category.entity.Category;
import com.example.laza.payment.dto.PaymentResponseDto;
import com.example.laza.payment.entity.Payment;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")

public class OrderResponseDto {

    private Integer id;

    private List<CategoryResponseDto> categories;

    private int howMuch;

    private AddressResponseDto address;

    private PaymentResponseDto payment;

    private int subtotal;
    private int shippingCost;
    private int total;
}
