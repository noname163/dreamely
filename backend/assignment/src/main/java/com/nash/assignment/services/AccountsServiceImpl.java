package com.nash.assignment.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.AccountStatus;
import com.nash.assignment.constant.UserRole;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.dto.request.AccountUpdateDto;
import com.nash.assignment.dto.response.AccountRespone;
import com.nash.assignment.exceptions.BadRequestException;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.mapper.AccountMapper;
import com.nash.assignment.modal.Account;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.RolesRepositories;
import com.nash.assignment.services.interfaces.AccountService;

@Service
public class AccountsServiceImpl implements AccountService{

    AccountRepositories accountRepositories;

    RolesRepositories rolesRepositories;

    ModelMapper modelMapper;
    AccountMapper accountMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountsServiceImpl(AccountRepositories accountRepositories, RolesRepositories rolesRepositories,
            PasswordEncoder passwordEncoder, ModelMapper modelMapper, AccountMapper accountMapper) {
        this.accountRepositories = accountRepositories;
        this.rolesRepositories = rolesRepositories;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.accountMapper = accountMapper;
    }
    

    public AccountsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public Account insert(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepositories.save(account);
    }

    @Override
    public AccountDto insertAccounts(AccountDto accountDto) {
        if (accountRepositories.findByPhoneNumber(accountDto.getPhoneNumber()) != null) {
            throw new InformationNotValidException("This Phonenumber Already Exist.");
        }
        if(accountRepositories.findByEmail(accountDto.getEmail())!=null){
            throw new InformationNotValidException("This Email Already Exist.");
        }
        accountDto.setRole(UserRole.ROLE_USER);
        accountDto.setStatus(AccountStatus.ACTIVE);
        accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        Account account = modelMapper.map(accountDto, Account.class);
        account = accountRepositories.save(account);

        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> list = accountRepositories.findAll();
        if (null == list || list.isEmpty()) {
            throw new ObjectNotFoundException("Account List Is Empty.");
        }
        return list.stream().map(account -> modelMapper.map(account, AccountDto.class)).collect(Collectors.toList());
    }

    public AccountDto getAccountById(long id) {
        Optional<Account> accountOtp = accountRepositories.findById(id);
        if (accountOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Account With ID: " + id);
        }
        Account account = accountOtp.get();
        return modelMapper.map(account, AccountDto.class);
    }

    public AccountDto getAccountByPhonenumber(String phone) {
        Account account = accountRepositories.findByPhoneNumber(phone);
        if (account==null) {
            throw new ObjectNotFoundException("Cannot Find Account With Phone: " + phone);
        }
        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto updateAccountStatus(long id, AccountStatus statusValue) {
        Optional<Account> accountOtp = accountRepositories.findById(id);
        if (accountOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Account With Id: " + id);
        }
        Account account = accountOtp.get();
        account.setStatus(statusValue);

        accountRepositories.save(account);
        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto updateAccountInformation(AccountDto accountValue) {
        Account account = accountRepositories.findByPhoneNumber(accountValue.getPhoneNumber());
        if (account == null) {
            throw new ObjectNotFoundException("Cannot Find Account With Phonenumber: " + accountValue.getPhoneNumber());
        }
        account.setFullName(accountValue.getFullName());
        account.setImage(accountValue.getImage());
        account = accountRepositories.save(account);

        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto updateAccountRole(long id) {
        Optional<Account> accountOtp = accountRepositories.findById(id);

        if (accountOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Found Account With Id: " + id);

        }
        Account account = accountOtp.get();
        account.setRole(UserRole.ROLE_ADMIN);
        accountRepositories.save(account);

        return modelMapper.map(account, AccountDto.class);
    }

    public void editAccount(AccountUpdateDto accountUpdateDto){
        Account account = accountRepositories.findByEmail(accountUpdateDto.getEmail());
        if(account == null){
            throw new BadRequestException("Cannot found account with email " + accountUpdateDto.getEmail());
        }
        account.setFullName(accountUpdateDto.getFullName());
        account.setDescription(accountUpdateDto.getDescription());
        account.setPhoneNumber(accountUpdateDto.getPhoneNumber());
        accountRepositories.save(account);
    }

    public AccountRespone getAccountByEmail(String email){
        Account account = accountRepositories.findByEmail(email);
        if(account == null ){
            throw new BadRequestException("Cannot found account with email " + email);
        }
        return accountMapper.mapEntityToRespone(account);
    }

}
