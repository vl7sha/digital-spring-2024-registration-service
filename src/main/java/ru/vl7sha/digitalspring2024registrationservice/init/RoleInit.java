package ru.vl7sha.digitalspring2024registrationservice.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.vl7sha.digitalspring2024registrationservice.service.RoleService;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleInit implements CommandLineRunner {

    private final RoleService service;

    @Override
    public void run(String... args) {
        List<String> roles = List.of(
                "ROLE_ADMIN",
                "ROLE_USER"
        );

        for (var roleName : roles) {
            service.findOrCreateByName(roleName);
        }

        log.info("Роли добавлены");
    }
}
