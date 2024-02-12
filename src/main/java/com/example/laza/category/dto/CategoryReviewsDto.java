package com.example.laza.category.dto;

import com.example.laza.review.dto.ReviewCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryReviewsDto {

    private Integer categoryId;
    private ReviewCreateDto review;
}
