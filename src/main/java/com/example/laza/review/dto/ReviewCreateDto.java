package com.example.laza.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewCreateDto {

    private String userName;
    private String comment;
    private double rating;
    private Date date;

}
