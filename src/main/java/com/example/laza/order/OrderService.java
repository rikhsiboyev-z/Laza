package com.example.laza.order;

import com.example.laza.address.AddressRepository;
import com.example.laza.address.entity.Address;
import com.example.laza.cart.CartRepository;
import com.example.laza.cart.dto.CartCreateDto;
import com.example.laza.cart.entity.Cart;
import com.example.laza.category.CategoryRepository;
import com.example.laza.category.entity.Category;
import com.example.laza.order.dto.OrderCreateDto;
import com.example.laza.order.dto.OrderResponseDto;
import com.example.laza.order.entity.Order;
import com.example.laza.payment.PaymentRepository;
import com.example.laza.payment.entity.Payment;
import com.example.laza.user.UserRepository;
import com.example.laza.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;


    public OrderResponseDto create(Integer userId, OrderCreateDto orderCreateDto) {

        Category category = categoryRepository.findById(orderCreateDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("category id not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user id not found"));


        userRepository.save(user);

        Address address = mapper.map(orderCreateDto.getAddress(), Address.class);

        Payment payment = mapper.map(orderCreateDto.getPayment(), Payment.class);

        if (category.getHowMuch() < orderCreateDto.getHowMuch()) {
            throw new EntityNotFoundException("category remains %d".formatted(category.getHowMuch()));
        }
        CartCreateDto cartCreateDto = new CartCreateDto();
        cartCreateDto.setUser(user);
        cartCreateDto.getOrders().add(orderCreateDto);
        Cart cart = mapper.map(cartCreateDto, Cart.class);

        user.getCarts().add(cart);
        cartRepository.save(cart);


        int much = category.getHowMuch() - orderCreateDto.getHowMuch();
        Order order = mapper.map(orderCreateDto, Order.class);

        order.getCarts().add(cart);
        order.getCategories().add(category);
        order.setSubtotal(order.getCategories().stream().mapToInt(Category::getPrice).sum());
        order.setShippingCost(10);
        order.setTotal(category.getPrice() + 10);


        category.getOrders().add(order);
        category.setHowMuch(much);
        categoryRepository.save(category);

        addressRepository.save(address);
        paymentRepository.save(payment);

        order.setAddress(address);
        order.setPayment(payment);


        return mapper.map(orderRepository.save(order), OrderResponseDto.class);

    }
}
