package com.example.Internet.Story.service;

import com.example.Internet.Story.entity.Customer;
import com.example.Internet.Story.entity.RefreshToken;
import com.example.Internet.Story.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken createRefreshToken(Customer customer) {
        refreshTokenRepository.deleteByCustomer(customer);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCustomer(customer);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Срок действия токена обновления истёк. Пожалуйста, отправьте новый запрос на вход");
        }
        return token;
    }

    @Transactional
    public void deleteByCustomer(Customer customer){
        refreshTokenRepository.deleteByCustomer(customer);
    }
}