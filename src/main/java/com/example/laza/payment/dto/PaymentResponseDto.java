package com.example.laza.payment.dto;

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
public class PaymentResponseDto {

    private Integer id;
    private String cardOwner;
    private String cardNumber;
    private String EXP;
    private String CVV;

}
