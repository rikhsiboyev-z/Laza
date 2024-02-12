package com.example.laza.review;

import com.example.laza.category.dto.CategoryResponseDto;
import com.example.laza.category.dto.CategoryUpdateDto;
import com.example.laza.category.entity.Category;
import com.example.laza.review.dto.ReviewCreateDto;
import com.example.laza.review.dto.ReviewResponseDto;
import com.example.laza.review.dto.ReviewUpdateDto;
import com.example.laza.review.entity.Review;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper mapper;


    public ReviewResponseDto create(ReviewCreateDto reviewCreateDto) {

        Review review = mapper.map(reviewCreateDto, Review.class);

        return mapper.map(reviewRepository.save(review), ReviewResponseDto.class);
    }

    public Page<ReviewResponseDto> getALl(Pageable pageable) {
        return reviewRepository.findAll(pageable)
                .map(category -> mapper.map(category, ReviewResponseDto.class));

    }

    public ReviewResponseDto getById(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("review id not found"));

        return mapper.map(review, ReviewResponseDto.class);
    }

    public ReviewResponseDto update(Integer id, ReviewUpdateDto reviewUpdateDto) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("review id not found"));

        mapper.map(reviewUpdateDto, review);

        reviewRepository.save(review);
        return mapper.map(review, ReviewResponseDto.class);
    }

    public ReviewResponseDto patch(Integer id, ReviewUpdateDto reviewUpdate) throws IllegalAccessException, NoSuchFieldException {

        Review entity = reviewRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Class<?> entityClass = entity.getClass();
        Class<?> patchDtoClass = reviewUpdate.getClass();

        for (Field field : patchDtoClass.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(reviewUpdate);
            if (value != null) {
                Field entityClassField = entityClass.getDeclaredField(field.getName());
                entityClassField.setAccessible(true);
                entityClassField.set(entity, value);
            }
        }
        Review review = reviewRepository.save(entity);
        return mapper.map(review, ReviewResponseDto.class);
    }

    public void delete(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("review id not found"));
        reviewRepository.delete(review);

    }
}
