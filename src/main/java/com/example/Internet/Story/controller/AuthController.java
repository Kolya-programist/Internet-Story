package com.example.Internet.Story.controller;


import com.example.Internet.Story.config.service.JwtService;
import com.example.Internet.Story.entity.Customer;
import com.example.Internet.Story.entity.RefreshToken;
import com.example.Internet.Story.repository.RefreshTokenRepository;
import com.example.Internet.Story.service.CustomerService;
import com.example.Internet.Story.service.RefreshTokenService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final CustomerService customerService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenService refreshTokenService,
                          CustomerService customerService, RefreshTokenRepository refreshTokenRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.customerService = customerService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String email, @RequestParam String password){

        Customer customer = customerService.getByEmail(email);

        if(customer.getLockTime() != null && customer.getLockTime().isAfter(LocalDateTime.now())){
            throw new RuntimeException("Аккаунт заблокирован. Попробуйте позже");
        } else if(customer.getLockTime() != null && customer.getLockTime().isBefore(LocalDateTime.now())){
            customerService.resetFailedAttempts(customer);
        }


        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            customerService.resetFailedAttempts(customer);

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(customer);
            String role = authentication
                    .getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();
            String accessToken = jwtService.generateToken(customer.getEmail(), role);

            Map<String, String> response = new HashMap<>();
            response.put("token", accessToken);
            response.put("refreshToken", refreshToken.getToken());
            return response;
        } catch (Exception e){
            customerService.increaseFailedAttempts(customer);
            throw new BadCredentialsException("Неправильный логин и пароль");
        }
    }

    @PostMapping("/refresh")
    public Map<String, String> refreshToken(@RequestParam String refreshToken){
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token не найден"));

        refreshTokenService.verifyExpiration(token);

        Customer customer = token.getCustomer();

        String role = customer.getRole();
        String newAccessToken = jwtService.generateToken(customer.getEmail(), role);

        Map<String, String> response = new HashMap<>();
        response.put("token", newAccessToken);
        return response;
    }

    @PostMapping("/logout")
    public Map<String, String> logout(Authentication authentication){

        String email = authentication.getName();

        Customer customer = customerService.getByEmail(email);

        refreshTokenService.deleteByCustomer(customer);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Вы успешно вышли из системы!");
        return response;
    }
}
