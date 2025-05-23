package com.th.Service;

import com.th.DTO.AccountDTO;
import com.th.entity.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface AccountService  extends UserDetailsService {


}
