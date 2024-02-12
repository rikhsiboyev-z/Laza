package com.example.laza.cart.dto;

import com.example.laza.order.dto.OrderResponseDto;
import com.example.laza.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartResponseDto {

    private Integer id;
    @JsonIgnore
    private User user;
    private List<OrderResponseDto> orders;

}
