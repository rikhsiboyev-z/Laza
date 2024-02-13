package com.example.laza.category.dto;

import com.example.laza.category.entity.Brand;
import com.example.laza.category.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchBrandSizeDto {

    private List<Brand> brands;
    private List<Size> sizes;
}
