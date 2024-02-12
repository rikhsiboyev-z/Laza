package com.example.laza.user.dto;

import com.example.laza.address.dto.AddressResponseDto;
import com.example.laza.cart.dto.CartResponseDto;
import com.example.laza.cart.entity.Cart;
import com.example.laza.payment.dto.PaymentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private List<AddressResponseDto> addresses;
    private List<PaymentResponseDto> payments;
    private List<CartResponseDto> carts;


}
