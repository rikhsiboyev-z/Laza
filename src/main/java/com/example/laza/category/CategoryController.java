package com.example.laza.category;

import com.example.laza.category.dto.CategoryCreateDto;
import com.example.laza.category.dto.CategoryResponseDto;
import com.example.laza.category.dto.CategoryReviewsDto;
import com.example.laza.category.dto.CategoryUpdateDto;
import com.example.laza.order.OrderService;
import com.example.laza.order.dto.OrderCreateDto;
import com.example.laza.order.dto.OrderResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService service;
    private final OrderService orderService;

    @PostMapping(value = "/upload/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFileUser(@RequestParam("file") MultipartFile file, @RequestParam Integer categoryId) {
        return switch (Objects.requireNonNull(file.getContentType())) {
            case MediaType.IMAGE_GIF_VALUE,
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE -> {
                String photo = service.photo(file, categoryId);
                yield ResponseEntity.ok(photo);
            }
            default -> {
                log.error("Unsupported filetype: {}", file.getContentType());
                throw new UnsupportedMediaTypeStatusException(
                        String.format("Unsupported filetype: %s", file.getContentType()));
            }
        };

    }


    @PostMapping()
    public ResponseEntity<CategoryResponseDto> create(@RequestBody @Valid CategoryCreateDto createDto) {

        CategoryResponseDto categoryResponseDto = service.create(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDto);
    }

    @PostMapping("/add/reviews")
    public ResponseEntity<CategoryResponseDto> addReviews(@RequestBody @Valid CategoryReviewsDto categoryReviewsDto) {
        CategoryResponseDto categoryResponseDto = service.addReviews(categoryReviewsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDto);
    }

    @PostMapping("/{userId}/add/to-cart")
    public ResponseEntity<OrderResponseDto> addToCart(@PathVariable Integer userId, @RequestBody @Valid OrderCreateDto orderCreateDto) {
        OrderResponseDto orderResponseDto = orderService.create(userId, orderCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDto);
    }

    @GetMapping()
    public ResponseEntity<Page<CategoryResponseDto>> getAll(Pageable pageable) {
        Page<CategoryResponseDto> page = service.getALl(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable Integer id) {
        CategoryResponseDto categoryResponseDto = service.getById(id);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(
            @PathVariable Integer id,
            @RequestBody @Valid CategoryUpdateDto categoryUpdateDto) {

        CategoryResponseDto categoryResponseDto = service.update(id, categoryUpdateDto);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> patch(
            @PathVariable Integer id,
            @RequestBody @Valid CategoryUpdateDto categoryUpdateDto)
            throws NoSuchFieldException, IllegalAccessException {

        CategoryResponseDto categoryResponseDto = service.patch(id, categoryUpdateDto);
        return ResponseEntity.ok(categoryResponseDto);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete");
    }


}
