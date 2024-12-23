package com.example.ecommerce.controllers;

import com.example.ecommerce.dto.AuthResponse;
import com.example.ecommerce.dto.ErrorResponse;
import com.example.ecommerce.dto.user.LoginDto;
import com.example.ecommerce.dto.user.SignupDto;
import com.example.ecommerce.errors.EmailInvalidException;
import com.example.ecommerce.errors.UserAlreadyExists;
import com.example.ecommerce.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        AuthResponse response = authService.login(loginDto);

        if (response.token().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDto userDto) {
        try {
            AuthResponse response = authService.create(userDto);

            return ResponseEntity.ok(response);
        } catch (UserAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
        } catch (EmailInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
