package com.example.laza.category;

import com.example.laza.category.entity.Brand;
import com.example.laza.category.entity.Category;
import com.example.laza.category.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByBrandInOrSizeIn(List<Brand> brands, List<Size> sizes);


}
