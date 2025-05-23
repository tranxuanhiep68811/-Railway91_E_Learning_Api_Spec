package com.th.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tên tài khoản không được để trống")
    @Column(name = "User_Name", unique = true, nullable = false, columnDefinition = "text")
    private String userName;

    @Size(min = 6, max = 10, message = "Mật khẩu phải từ 6 đến 10 ký tự")
    @Column(name = "Password", nullable = false, columnDefinition = "text")
    private String password;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    @Column(name = "Email", unique = true, nullable = false, columnDefinition = "text")
    private String email;

    @NotNull(message = "Vai trò không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private Role role;

    @NotNull(message = "Ngày tạo không được để trống")
    @Column(name = "Created_Date", nullable = false, columnDefinition = "text")
    private LocalDate createdDate;

    @NotNull(message = "Ngày cập nhật không được để trống")
    @Column(name = "Updated_Date", nullable = false, columnDefinition = "text")
    private LocalDate updatedDate;

    @NotNull(message = "Ngày sinh không được để trống")
    @Column(name = "Date_Of_Birth", nullable = false, columnDefinition = "text")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Column(name = "Address", nullable = false, columnDefinition = "text")
    private String address;

    public Account(Integer id, String userName, String password, String email, Role role, LocalDate createdDate, LocalDate updatedDate, LocalDate dateOfBirth, String address) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public Account() {
        // Default constructor
    }
}
