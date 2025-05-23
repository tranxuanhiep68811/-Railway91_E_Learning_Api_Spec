package com.th.config;

import com.th.DTO.AccountDTO;
import com.th.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public void updateEntityFromDTO(AccountDTO dto, Account entity) {

        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setUpdatedDate(dto.getUpdatedDate());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setRole(dto.getRole());
    }
}