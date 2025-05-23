package com.th.controller;

import com.th.DTO.AccountDTO;
import com.th.DTO.RegisterRequestDTO;
import com.th.entity.Account;
import com.th.entity.Role;
import com.th.repository.AccountRepository;
import com.th.Service.AccountServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RequestMapping("/accounts")
@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Lấy tất cả tài khoản
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Account> accounts = accountRepo.findAll();

        List<AccountDTO> accountDTOs = accounts.stream().map(account -> {
            AccountDTO dto = new AccountDTO();
            dto.setId(account.getId());
            dto.setUserName(account.getUserName());
            dto.setPassword(account.getPassword());
            dto.setEmail(account.getEmail());
            dto.setRole(account.getRole());
            dto.setCreatedDate(account.getCreatedDate());
            dto.setUpdatedDate(account.getUpdatedDate());
            dto.setDateOfBirth(account.getDateOfBirth());
            dto.setAddress(account.getAddress());
            return dto;
        }).toList();

        return ResponseEntity.ok(accountDTOs);
    }

    // update tài khoản
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody @Valid AccountDTO dto) {
        accountServiceImpl.updateAccount(id, dto);
        return ResponseEntity.ok("Update Successfully!");
    }
}
