package ru.vl7sha.digitalspring2024registrationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vl7sha.digitalspring2024registrationservice.exception.ApiException;
import ru.vl7sha.digitalspring2024registrationservice.model.dto.RequestRestorePasswordDTO;
import ru.vl7sha.digitalspring2024registrationservice.model.dto.RestorePasswordDTO;
import ru.vl7sha.digitalspring2024registrationservice.service.RestorePasswordService;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class RestorePasswordController extends AbstractController {
    private RestorePasswordService restorePasswordService;

    @PostMapping("/request")
    public ResponseEntity requestRestorePassword(
            @RequestBody RequestRestorePasswordDTO requestRestorePasswordDTO
    ) {
        restorePasswordService.approveRestorePassword(requestRestorePasswordDTO.getEmail());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/restore")
    public ResponseEntity<Void> restorePassword(
            @RequestParam String token,
            @RequestBody RestorePasswordDTO restorePasswordDTO
    ) {
        if (!Objects.equals(
                restorePasswordDTO.getPasswordFirst(),
                restorePasswordDTO.getPasswordSecond())
        ) {
            throw new ApiException("Пароли не совпадают");
        }

        restorePasswordService.restorePassword(token, restorePasswordDTO.getPasswordFirst());

        return ResponseEntity.ok().build();
    }
}
