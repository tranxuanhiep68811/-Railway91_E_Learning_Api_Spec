package com.th.DTO;

import com.th.entity.Role;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterRequestDTO {
    private String userName;

    private String password;

    private String email;

    private Role role;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    private LocalDate dateOfBirth;

    private String address;
}
