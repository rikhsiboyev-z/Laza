package com.example.laza.category;

import com.example.laza.category.dto.CategoryCreateDto;
import com.example.laza.category.dto.CategoryResponseDto;
import com.example.laza.category.dto.CategoryReviewsDto;
import com.example.laza.category.dto.CategoryUpdateDto;
import com.example.laza.category.entity.Category;
import com.example.laza.review.ReviewRepository;
import com.example.laza.review.dto.ReviewCreateDto;
import com.example.laza.review.entity.Review;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final ReviewRepository reviewRepository;
    @Value("${service.upload.dir}")
    private String uploadDir;


    public CategoryResponseDto create(CategoryCreateDto createDto) {

        Category category = mapper.map(createDto, Category.class);
        return mapper.map(categoryRepository.save(category), CategoryResponseDto.class);

    }

    public CategoryResponseDto addReviews(CategoryReviewsDto categoryReviewsDto) {

        Category category = categoryRepository.findById(categoryReviewsDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        ReviewCreateDto reviewDto = categoryReviewsDto.getReview();
        Review review = mapper.map(reviewDto, Review.class);

        review.setCategory(category);

        review = reviewRepository.save(review);

        category.getReviews().add(review);

        categoryRepository.save(category);

        return mapper.map(category, CategoryResponseDto.class);


    }

    public Page<CategoryResponseDto> getALl(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(category -> mapper.map(category, CategoryResponseDto.class));

    }

    public CategoryResponseDto getById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("category id not found"));

        return mapper.map(category, CategoryResponseDto.class);
    }

    public CategoryResponseDto update(Integer id, CategoryUpdateDto categoryUpdateDto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("category id not found"));

        mapper.map(categoryUpdateDto, category);

        categoryRepository.save(category);
        return mapper.map(category, CategoryResponseDto.class);
    }

    public CategoryResponseDto patch(Integer id, CategoryUpdateDto categoryUpdateDto) throws IllegalAccessException, NoSuchFieldException {

        Category entity = categoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Class<?> entityClass = entity.getClass();
        Class<?> patchDtoClass = categoryUpdateDto.getClass();

        for (Field field : patchDtoClass.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(categoryUpdateDto);
            if (value != null) {
                Field entityClassField = entityClass.getDeclaredField(field.getName());
                entityClassField.setAccessible(true);
                entityClassField.set(entity, value);
            }
        }
        Category category = categoryRepository.save(entity);
        return mapper.map(category, CategoryResponseDto.class);
    }

    public void delete(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("category id not found"));
        categoryRepository.delete(category);

    }


    public String photo(MultipartFile file, Integer categoryId) {

        if (file.isEmpty()) {
            log.error("Empty file uploaded");
            throw new IllegalArgumentException("Empty file uploaded");
        }

        try {

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("category id not found"));

            File destFile = Paths.get(uploadDir, file.getOriginalFilename()).toFile();
            file.transferTo(destFile);
            log.info("Uploaded: {}", destFile);

            category.setPhoto(destFile.getPath());

            categoryRepository.save(category);

        } catch (IOException e) {
            log.error("Error uploading file: {}", e.getMessage());
            throw new RuntimeException("Error uploading file", e);
        }
        return "success";
    }
}

