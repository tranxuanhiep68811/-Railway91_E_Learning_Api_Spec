package com.th.repository;

import com.th.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByUserName(String userName);

    Account findByEmail(String email);
}
