package ru.vl7sha.digitalspring2024registrationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestorePasswordDTO {
    private String passwordFirst;
    private String passwordSecond;
}
