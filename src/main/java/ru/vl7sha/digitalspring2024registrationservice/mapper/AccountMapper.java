package ru.vl7sha.digitalspring2024registrationservice.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.vl7sha.digitalspring2024registrationservice.model.dto.ResponseAccountDTO;
import ru.vl7sha.digitalspring2024registrationservice.model.dto.RequestRegistrationAccountDTO;
import ru.vl7sha.digitalspring2024registrationservice.model.entity.Account;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final ModelMapper modelMapper;

    public Account map(RequestRegistrationAccountDTO rradto) {
        Account account = modelMapper.map(rradto, Account.class);
        account.setPassword(rradto.getPasswordFirst());
        return account;
    }

    public ResponseAccountDTO map(Account account) {
        return modelMapper.map(account, ResponseAccountDTO.class);
    }
}
