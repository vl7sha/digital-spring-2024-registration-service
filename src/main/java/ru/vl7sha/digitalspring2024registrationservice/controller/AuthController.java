package ru.vl7sha.digitalspring2024registrationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vl7sha.digitalspring2024registrationservice.exception.ApiException;
import ru.vl7sha.digitalspring2024registrationservice.mapper.AccountMapper;
import ru.vl7sha.digitalspring2024registrationservice.model.dto.AuthRequestDTO;
import ru.vl7sha.digitalspring2024registrationservice.model.dto.AuthResponseDTO;
import ru.vl7sha.digitalspring2024registrationservice.model.dto.ResponseAccountDTO;
import ru.vl7sha.digitalspring2024registrationservice.model.entity.Account;
import ru.vl7sha.digitalspring2024registrationservice.security.UserDetailsImpl;
import ru.vl7sha.digitalspring2024registrationservice.security.jwt.JwtUtil;
import ru.vl7sha.digitalspring2024registrationservice.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AuthController {

    private final AccountMapper accountMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthService accountService;

    @Value("${jwt.tokenExpiresIn}")
    private int tokenExpiresIn;

    @GetMapping("/me")
    @Secured("ROLE_USER")
    public ResponseEntity<ResponseAccountDTO> getMe(){
        return ResponseEntity.ok(
                accountMapper.map(
                        accountService.getAuthenticated()
                )
        );
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequestDTO.getEmail(),
                authRequestDTO.getPassword()
        );

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new ApiException("Неправильные email или пароль");
        }

        Account account = ((UserDetailsImpl)authentication.getPrincipal()).getUser();

        if (Boolean.FALSE.equals(account.getEnable())){
            throw new ApiException("Ваш аккаунт не активирован");
        }

        String token = jwtUtil.generateToken(
                account.getEmail(),
                account.getRole().getName(),
                tokenExpiresIn
        );

        return ResponseEntity.ok(new AuthResponseDTO(
                token,
                tokenExpiresIn
        ));
    }
}
