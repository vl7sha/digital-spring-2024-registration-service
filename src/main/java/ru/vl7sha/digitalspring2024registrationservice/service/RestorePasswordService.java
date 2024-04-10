package ru.vl7sha.digitalspring2024registrationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vl7sha.digitalspring2024registrationservice.exception.ApiException;
import ru.vl7sha.digitalspring2024registrationservice.model.entity.Account;
import ru.vl7sha.digitalspring2024registrationservice.model.entity.Token;
import ru.vl7sha.digitalspring2024registrationservice.model.entity.TokenType;
import ru.vl7sha.digitalspring2024registrationservice.repository.AccountRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Transactional
@RequiredArgsConstructor
@Service
public class RestorePasswordService {
    private final AccountRepository accountRepository;
    private final TokenService tokenService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Async
    public void approveRestorePassword(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ApiException("Такого аккаунта нет в системе")
                );

        Token token = tokenService.createToken(account, TokenType.RESTORE);

        mailService.sendToken(account.getEmail(), token);
    }

    public void restorePassword(String token, String password) {
        Token restoreToken = tokenService.find(token).orElseThrow(
                () -> new ApiException("такого токена нет")
        );

        if (LocalDateTime.now().isAfter(restoreToken.getExpireAt())) {
            throw new IllegalStateException();
        }

        if (Objects.equals(restoreToken.getTokenType(), TokenType.RESTORE)) {
            throw new ApiException("Не тот токен");
        }

        Account account = restoreToken.getAccount();

        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
        tokenService.confirm(restoreToken);
    }
}
