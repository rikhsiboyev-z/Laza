package com.example.laza.review.dto;

import com.example.laza.category.entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewResponseDto {

    private Integer id;
    private String userName;
    private String comment;
    private double rating;
    private Date date;
    @JsonIgnore
    private Category category;
}
