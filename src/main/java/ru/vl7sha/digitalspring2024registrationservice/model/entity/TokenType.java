package ru.vl7sha.digitalspring2024registrationservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.type.ListType;

@Getter
@AllArgsConstructor
public enum TokenType {
    CONFIRM(0),
    RESTORE(1);

    private final int id;

    public static TokenType byId(int id) {
        for (TokenType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }
}
