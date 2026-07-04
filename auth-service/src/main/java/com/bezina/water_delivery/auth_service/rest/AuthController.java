package com.bezina.water_delivery.auth_service.rest;

import com.bezina.water_delivery.auth_service.DAO.UserRepository;
import com.bezina.water_delivery.auth_service.DTO.AuthResponse;
import com.bezina.water_delivery.auth_service.DTO.LoginRequest;
import com.bezina.water_delivery.auth_service.DTO.RegisterRequest;
import com.bezina.water_delivery.auth_service.entity.User;
import com.bezina.water_delivery.auth_service.service.AuthenticationService;
import com.bezina.water_delivery.auth_service.service.CustomUserDetailsService;
import com.bezina.water_delivery.auth_service.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "*") // или конкретный фронтенд URL
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("hello successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
     return authenticationService.login(request);
    }
}
