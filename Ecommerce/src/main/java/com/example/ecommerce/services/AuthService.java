package com.example.ecommerce.services;

import com.example.ecommerce.dto.AuthResponse;
import com.example.ecommerce.dto.user.LoginDto;
import com.example.ecommerce.dto.user.SignupDto;
import com.example.ecommerce.dto.user.UserDto;
import com.example.ecommerce.errors.EmailInvalidException;
import com.example.ecommerce.errors.UserAlreadyExists;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.Client;
import com.example.ecommerce.models.Role;
import com.example.ecommerce.models.User;
import com.example.ecommerce.repositories.ClientRepository;
import com.example.ecommerce.repositories.UserRepository;
import com.example.ecommerce.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ClientRepository clientRepository;

    public void initializeClient(User user) {
        if (user.getClient() == null) {
            Client client = new Client();
            client.setUser(user);

            Cart cart = new Cart();
            cart.setClient(client);
            client.setCart(cart);
            user.setClient(client);

            clientRepository.save(client);
        }
    }

    public AuthResponse create(SignupDto userDto) throws UserAlreadyExists, EmailInvalidException {
        // Check if the email is valid
        if (!emailIsValid(userDto.getEmail())) {
            throw new EmailInvalidException();
        }

        // Check if the user already exists
        if (userRepository.findByEmail(userDto.getEmail()).isPresent() || userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserAlreadyExists();
        }

        User user = mapUserDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        var savedUser = userRepository.save(user);
        initializeClient(user);

        savedUser = userRepository.save(user);

        return new AuthResponse(jwtUtil.generateToken(savedUser));
    }

    public AuthResponse login(LoginDto loginDto) {
        if (loginDto.getUsername() == null || loginDto.getPassword() == null) {
            return new AuthResponse("");
        }

        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!checkPassword(user, loginDto.getPassword())) {
            return new AuthResponse("");
        }

        return new AuthResponse(jwtUtil.generateToken(user));
    }

    private boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    private boolean emailIsValid(String email) {
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }

    public User mapUserDtoToUser(SignupDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
