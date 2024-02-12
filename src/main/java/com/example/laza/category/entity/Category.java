package com.example.laza.category.entity;

import com.example.laza.order.entity.Order;
import com.example.laza.review.entity.Review;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String photo;
    private String name;
    @Enumerated(EnumType.STRING)
    private List<Size> size;
    private int price;
    private int howMuch;

    @Column(length = 1024 * 4)
    private String description;

    @Enumerated(EnumType.STRING)
    private List<Brand> brand;

    @OneToMany(mappedBy = "category")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties("category")
    private List<Review> reviews;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "categories")
    @JsonIgnoreProperties("category")
    private List<Order> orders;
}
