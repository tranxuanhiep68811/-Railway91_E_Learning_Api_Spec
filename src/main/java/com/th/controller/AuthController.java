package com.th.controller;

import com.th.DTO.RegisterRequestDTO;
import com.th.entity.Account;
import com.th.entity.Role;
import com.th.repository.AccountRepository;
import com.th.Service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Đăng ký tài khoản
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        // Kiểm tra username/email đã tồn tại
        if (accountRepo.findByUserName(request.getUserName()) != null) {
            return ResponseEntity.badRequest().body("Username đã tồn tại");
        }

        if (accountRepo.findByEmail(request.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email đã tồn tại");
        }

        // Tạo account mới
        Account account = new Account();
        account.setUserName(request.getUserName());
        account.setPassword(passwordEncoder.encode(request.getPassword())); // mã hóa
        account.setEmail(request.getEmail());
        account.setDateOfBirth(request.getDateOfBirth());
        account.setAddress(request.getAddress());
        account.setRole(request.getRole() != null ? request.getRole() : Role.USER); // mặc định là USER

        // Set ngày tạo/cập nhật
        LocalDate now = LocalDate.now();
        account.setCreatedDate(now);
        account.setUpdatedDate(now);

        accountRepo.save(account);

        return ResponseEntity.ok("Đăng ký thành công!");
    }
}
