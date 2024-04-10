package ru.vl7sha.digitalspring2024registrationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vl7sha.digitalspring2024registrationservice.exception.ApiException;
import ru.vl7sha.digitalspring2024registrationservice.mapper.AccountMapper;
import ru.vl7sha.digitalspring2024registrationservice.model.dto.RequestRegistrationAccountDTO;
import ru.vl7sha.digitalspring2024registrationservice.service.RegistrationService;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class RegistrationController extends AbstractController {

    private final RegistrationService accountService;
    private final AccountMapper accountMapper;

    @PostMapping("/sing-up")
    public ResponseEntity<Void> singUp(
            @RequestBody RequestRegistrationAccountDTO requestRegistrationAccountDTO
    ) {
        logRequest(requestRegistrationAccountDTO);

        if (!Objects.equals(
                requestRegistrationAccountDTO.getPasswordFirst(),
                requestRegistrationAccountDTO.getPasswordSecond())
        ) {
            throw new ApiException("Пароли не совпадают");
        }


        accountService.singUp(accountMapper.map(requestRegistrationAccountDTO));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmAccount(
            @RequestParam String token
    ) {
        logRequest("Token : " + token);

        accountService.tryEnableAccount(token);

        return ResponseEntity.ok().build();
    }
}
