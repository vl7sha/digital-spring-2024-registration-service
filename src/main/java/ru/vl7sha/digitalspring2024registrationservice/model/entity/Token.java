package ru.vl7sha.digitalspring2024registrationservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;



import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "token")
public class Token extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;

    @Enumerated(EnumType.ORDINAL)
    private TokenType tokenType;

    private Boolean isConfirmed;
    private String token;
}
