package com.bezina.water_delivery.auth_service.service;

import com.bezina.water_delivery.auth_service.DAO.UserRepository;
import com.bezina.water_delivery.auth_service.DTO.AuthResponse;
import com.bezina.water_delivery.auth_service.DTO.LoginRequest;
import com.bezina.water_delivery.auth_service.DTO.RegisterRequest;
import com.bezina.water_delivery.auth_service.entity.User;
import com.bezina.water_delivery.auth_service.exception_handler.InvalidCredentialsException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public ResponseEntity<?> login(LoginRequest request) {
        // проверяем логин/пароль
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),
//                        request.getPassword()
//                )   );
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException();
        }


        // грузим пользователя
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // достаём роль
        String role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);

        // генерим токен
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token, role, request.getUsername()));
    }


    public ResponseEntity<String> register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}
