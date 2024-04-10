package ru.vl7sha.digitalspring2024registrationservice.model.dto;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRegistrationAccountDTO {
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String surname;
    @NotNull
    private String email;
    @NotNull
    private String passwordFirst;
    @NotNull
    private String passwordSecond;
}
