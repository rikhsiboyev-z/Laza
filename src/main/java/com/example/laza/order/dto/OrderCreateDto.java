package com.example.laza.order.dto;

import com.example.laza.address.dto.AddressCreateDto;
import com.example.laza.address.entity.Address;
import com.example.laza.cart.dto.CartCreateDto;
import com.example.laza.payment.dto.PaymentCreateDto;
import com.example.laza.payment.entity.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCreateDto {


    private Integer categoryId;

    private int howMuch;

    private AddressCreateDto address;
    private PaymentCreateDto payment;

}
