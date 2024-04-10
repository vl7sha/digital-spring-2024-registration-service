package ru.vl7sha.digitalspring2024registrationservice.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.vl7sha.digitalspring2024registrationservice.exception.ApiException;
import ru.vl7sha.digitalspring2024registrationservice.model.entity.Account;
import ru.vl7sha.digitalspring2024registrationservice.security.UserDetailsImpl;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Account> tryGetAuthenticated() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null || principal.equals("anonymousUser")) return Optional.empty();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        Account detachedAccount = userDetails.getUser();
        return Optional.ofNullable(entityManager.find(Account.class, detachedAccount.getId()));
    }

    public Account getAuthenticated() {
        return tryGetAuthenticated().orElseThrow(
                () -> new ApiException("аккаунт не найден")
        );
    }
}
