package com.nash.assignment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nash.assignment.dto.request.AccountUpdateDto;
import com.nash.assignment.dto.response.AccountRespone;
import com.nash.assignment.modal.Account;

@Component
public class AccountMapper {
    public Account mapUpdateDtoToEntity(AccountUpdateDto accountUpdateDto) {
        Account account = new Account();
        account.setDescription(accountUpdateDto.getDescription());
        account.setEmail(accountUpdateDto.getEmail());
        account.setFullName(accountUpdateDto.getFullName());
        return account;
    }

    public List<Account> mapUpdateDtoToEntities(List<AccountUpdateDto> accountUpdateDtos) {
        return accountUpdateDtos
                .stream()
                .map(this::mapUpdateDtoToEntity)
                .collect(Collectors.toList());
    }

    public AccountRespone mapEntityToRespone(Account account) {

        return AccountRespone
                .builder()
                .description(account.getDescription() == null ? "" : account.getDescription())
                .email(account.getEmail())
                .fullName(account.getFullName())
                .phoneNumber(account.getPhoneNumber())
                .username(account.getUsername())
                .build();
    }

    public List<AccountRespone> mapAccountRespones(List<Account> accounts) {
        return accounts
                .stream()
                .map(this::mapEntityToRespone)
                .collect(Collectors.toList());
    }
}
