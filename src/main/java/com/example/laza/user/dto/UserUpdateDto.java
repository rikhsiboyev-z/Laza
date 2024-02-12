package com.example.laza.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDto {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
