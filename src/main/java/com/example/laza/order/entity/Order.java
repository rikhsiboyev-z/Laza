package com.example.laza.order.entity;

import com.example.laza.address.entity.Address;
import com.example.laza.cart.entity.Cart;
import com.example.laza.category.entity.Category;
import com.example.laza.payment.entity.Payment;
import com.example.laza.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToMany
    @JoinTable(name = "order_category",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIgnoreProperties("category")
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();
    private int howMuch;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne()
    @JoinColumn(name = "address_id")
    private Address address;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne()
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToMany
    @JoinTable(name = "order_carts",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    private List<Cart> carts = new ArrayList<>();

    private int subtotal;
    private int shippingCost;
    private int total;


}

