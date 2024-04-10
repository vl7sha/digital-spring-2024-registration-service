package ru.vl7sha.digitalspring2024registrationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vl7sha.digitalspring2024registrationservice.model.entity.Account;
import ru.vl7sha.digitalspring2024registrationservice.model.entity.Token;
import ru.vl7sha.digitalspring2024registrationservice.model.entity.TokenType;
import ru.vl7sha.digitalspring2024registrationservice.repository.TokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {

    private final TokenRepository confirmTokenRepository;


    @Value("${token.confirm-expired-after}")
    private int confirmExpiredAfter;

    @Value("${token.restore-expired-after}")
    private int restoreExpiredAfter;

    @Transactional
    public Token createToken(Account account, TokenType tokenType) {
        return confirmTokenRepository.save(
                new Token()
                        .setAccount(account)
                        .setToken(UUID.randomUUID().toString())
                        .setIsConfirmed(false)
                        .setTokenType(tokenType)
                        .setExpireAt(
                                LocalDateTime.now().plusMinutes(
                                        tokenType == TokenType.CONFIRM ? confirmExpiredAfter : restoreExpiredAfter
                                )
                        )
        );
    }

    public Optional<Token> find(String token) {
        return confirmTokenRepository.findByToken(token);
    }

    @Transactional
    public void confirm(Token token) {
        token.setIsConfirmed(true);
        if (token.getId() == null) throw new IllegalStateException();
        confirmTokenRepository.save(token);
    }
}