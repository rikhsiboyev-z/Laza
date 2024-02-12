package com.example.laza.cart.dto;

import com.example.laza.order.dto.OrderCreateDto;
import com.example.laza.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartCreateDto {

    private User user;
    private List<OrderCreateDto> orders = new ArrayList<>();

}
