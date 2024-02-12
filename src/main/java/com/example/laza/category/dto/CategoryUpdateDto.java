package com.example.laza.category.dto;

import com.example.laza.category.entity.Brand;
import com.example.laza.category.entity.Size;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryUpdateDto {


    private String photo;
    private String name;
    private int howMuch;
    private List<Size> size;
    private int price;
    @Column(length = 1024 * 4)
    private String description;
    private List<Brand> brand;

}
