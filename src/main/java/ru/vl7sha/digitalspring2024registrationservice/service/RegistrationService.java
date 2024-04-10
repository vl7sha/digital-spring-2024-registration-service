package ru.vl7sha.digitalspring2024registrationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RegistrationService {

    private final AccountRepository accountRepository;
    private final TokenService tokenService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Transactional
    public void singUp(Account account) {
        Optional<Account> byEmail = accountRepository.findByEmail(account.getEmail());

        if (byEmail.isPresent()) {
            throw new ApiException("Такой email уже есть");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setEnable(false);
        account.setRole(roleService.findOrCreateByName("ROLE_USER"));

        try {
            Account created = accountRepository.save(account);
            Token token = tokenService.createToken(created, TokenType.CONFIRM);
            mailService.sendToken(created.getEmail(), token);
        }
        catch (MailSendException e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Transactional
    public void tryEnableAccount(String token) {
        Optional<Token> confirmTokenOpt = tokenService.find(token);
        if (confirmTokenOpt.isEmpty()) {
            throw new IllegalStateException();
        }
        Token confirmToken = confirmTokenOpt.get();
        if (LocalDateTime.now().isAfter(confirmToken.getExpireAt())) {
            throw new IllegalStateException();
        }
        if (Objects.equals(confirmToken.getTokenType(), TokenType.CONFIRM)) {
            throw new ApiException("Не тот токен");
        }
        if (Boolean.TRUE.equals(confirmToken.getIsConfirmed())) {
            throw new ApiException("Этот токен уже использовали");
        }

        Account account = confirmToken.getAccount();
        account.setEnable(true);
        accountRepository.save(account);
        tokenService.confirm(confirmToken);
    }

}
