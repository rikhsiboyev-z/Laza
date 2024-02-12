package com.example.laza.payment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentCreateDto {

    private String cardOwner;
    private String cardNumber;
    private String EXP;
    private String CVV;


}
