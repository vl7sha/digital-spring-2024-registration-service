package ru.vl7sha.digitalspring2024registrationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRestorePasswordDTO {
    private String email;
}
