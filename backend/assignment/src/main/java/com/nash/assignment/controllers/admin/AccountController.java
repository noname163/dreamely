package com.nash.assignment.controllers.admin;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nash.assignment.constant.AccountStatus;
import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.dto.request.AccountUpdateDto;
import com.nash.assignment.dto.response.AccountRespone;
import com.nash.assignment.services.AccountsServiceImpl;
import com.nash.assignment.services.PaginationServiceImpl;

@RestController
@RequestMapping("/admin/accounts")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AccountController {

    @Autowired
    AccountsServiceImpl accountsServiceImpl;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PaginationServiceImpl paginationServiceImpl;

    @GetMapping
    public ResponseEntity<List<AccountDto>> displayAccount() {
        List<AccountDto> accountList = accountsServiceImpl.getAllAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(
                accountList
                );
    }
    @GetMapping(value="/{page}/{item}")
    public ResponseEntity<List<AccountDto>> displayAccountPagination(@PathVariable int page, @PathVariable int item) {
        List<AccountDto> accountList = paginationServiceImpl.getAllAccountPagination(page, item);
        return ResponseEntity.status(HttpStatus.OK).body(
            accountList        
        );
    }

    @PatchMapping(value = "/active/{id}")
    public ResponseEntity<AccountDto> acctiveAccount(@PathVariable long id) {
        AccountDto accountDto =  accountsServiceImpl.updateAccountStatus(id, AccountStatus.ACTIVE);
        return ResponseEntity.status(HttpStatus.OK).body(accountDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable long id) {
        accountsServiceImpl.updateAccountStatus(id, AccountStatus.DEACTIVE);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/update-role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable long id) {
        accountsServiceImpl.updateAccountRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-information/{email}")
    public ResponseEntity<AccountRespone> getInformation(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(
            accountsServiceImpl.getAccountByEmail(email)
        );
    }
    @PostMapping(value = "/edit-information")
    public ResponseEntity<?> editInformation(@RequestBody AccountUpdateDto account){
        accountsServiceImpl.editAccount(account);
        return ResponseEntity.noContent().build();
    }
}
