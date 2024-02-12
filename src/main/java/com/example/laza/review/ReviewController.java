package com.example.laza.review;

import com.example.laza.review.dto.ReviewCreateDto;
import com.example.laza.review.dto.ReviewResponseDto;
import com.example.laza.review.dto.ReviewUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping()
    public ResponseEntity<ReviewResponseDto> create(@RequestBody @Valid ReviewCreateDto reviewCreateDto) {

        ReviewResponseDto reviewResponseDto = service.create(reviewCreateDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseDto);
    }

    @GetMapping()
    public ResponseEntity<Page<ReviewResponseDto>> getAll(Pageable pageable) {
        Page<ReviewResponseDto> page = service.getALl(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getById(@PathVariable Integer id) {
        ReviewResponseDto reviewresponseDto = service.getById(id);
        return ResponseEntity.ok(reviewresponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> update(
            @PathVariable Integer id,
            @RequestBody @Valid ReviewUpdateDto reviewUpdate) {

        ReviewResponseDto reviewresponseDto = service.update(id, reviewUpdate);
        return ResponseEntity.ok(reviewresponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> patch(
            @PathVariable Integer id,
            @RequestBody @Valid ReviewUpdateDto reviewUpdate)
            throws NoSuchFieldException, IllegalAccessException {

        ReviewResponseDto reviewresponseDto = service.patch(id, reviewUpdate);
        return ResponseEntity.ok(reviewresponseDto);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete");
    }

}
