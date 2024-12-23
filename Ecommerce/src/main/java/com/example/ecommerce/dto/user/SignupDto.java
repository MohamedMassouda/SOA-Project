package com.example.ecommerce.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class SignupDto {
    private String username;
    private String email;
    private String password;
}
