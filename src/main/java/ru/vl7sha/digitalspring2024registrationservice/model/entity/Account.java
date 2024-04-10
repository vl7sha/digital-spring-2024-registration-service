package ru.vl7sha.digitalspring2024registrationservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "account")
public class Account extends AbstractEntity {
    private String firstname;
    private String lastname;
    private String surname;
    private String email;
    private String password;
    private Boolean enable;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
