package com.example.laza.user;

import com.example.laza.address.dto.AddressCreateDto;
import com.example.laza.jwt.JwtService;
import com.example.laza.otp.dto.OtpVerifyDto;
import com.example.laza.payment.dto.PaymentCreateDto;
import com.example.laza.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserCreateDto userCreateDto) {

        UserResponseDto userResponseDto = userService.signUp(userCreateDto);

        String token = jwtService.generateToken(userCreateDto.getEmail(), Collections.emptyMap());

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(userResponseDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserResponseDto> signIn(@RequestBody @Valid UserSignInDto userSignInDto) {

        UserResponseDto userResponseDto = userService.signIn(userSignInDto);

        String token = jwtService.generateToken(userSignInDto.getFirstnameOrEmail(), Collections.emptyMap());

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(userResponseDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<UserResponseDto> forgotPassword(@RequestBody @Valid ForgotPassword email) {
        UserResponseDto userResponseDto = userService.forgotPassword(email);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/forgot-verify-code")
    public ResponseEntity<UserResponseDto> forgotPasswordVerify(@RequestBody @Valid OtpVerifyDto verifyDto) {

        UserResponseDto userResponseDto = userService.verifyForgotPassCode(verifyDto);

        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/{id}/add/address")
    public ResponseEntity<UserResponseDto> addUserAddress(@PathVariable Integer id, @RequestBody @Valid AddressCreateDto addressCreateDto) {
        UserResponseDto userResponseDto = userService.addUserAddress(id, addressCreateDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/{id}/add/card")
    public ResponseEntity<UserResponseDto> addUserCard(@PathVariable Integer id, @RequestBody @Valid PaymentCreateDto createDto) {
        UserResponseDto userResponseDto = userService.addUserCard(id, createDto);
        return ResponseEntity.ok(userResponseDto);
    }


    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getALl(Pageable pageable) {

        Page<UserResponseDto> page = userService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Integer id) {
        UserResponseDto userResponseDto = userService.getById(id);
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
            @PathVariable Integer id,
            @RequestBody @Valid UserUpdateDto updateDto) {

        UserResponseDto userResponseDto = userService.update(id, updateDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> patch(
            @PathVariable Integer id,
            @RequestBody @Valid UserUpdateDto updateDto)
            throws NoSuchFieldException, IllegalAccessException {

        UserResponseDto userResponseDto = userService.patch(id, updateDto);
        return ResponseEntity.ok(userResponseDto);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete");
    }
}
