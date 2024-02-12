package com.example.laza.category.dto;

import com.example.laza.category.entity.Brand;
import com.example.laza.category.entity.Size;
import com.example.laza.order.dto.OrderResponseDto;
import com.example.laza.order.entity.Order;
import com.example.laza.review.dto.ReviewResponseDto;
import com.example.laza.review.entity.Review;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
public class CategoryResponseDto {

    private Integer id;
    private String photo;
    private String name;
    private int howMuch;
    private List<Size> size;
    private int price;
    private String description;
    private List<Brand> brand;
    private List<ReviewResponseDto> reviews;
    private List<OrderResponseDto> orders;


}
