package com.th.Service;

import com.th.DTO.AccountDTO;
import com.th.entity.Account;
import com.th.repository.AccountRepository;
import com.th.config.AccountMapper;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

        @Autowired
        private AccountRepository accountRepo;
        @Autowired
        private AccountMapper accountMapper;

        @Override
        public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
            Account account = accountRepo.findByUserName(userName);
            if (account == null) {
                throw new UsernameNotFoundException("Account is incorrect!");
            }

            // Lấy quyền từ Role Enum
            String role = account.getRole().name(); // ví dụ: ADMIN, USER
            return new User(userName, account.getPassword(),
                    AuthorityUtils.createAuthorityList("ROLE_" + role));
        }

        public void updateAccount(Integer id, AccountDTO dto) {
            Account account = accountRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found!"));

            accountMapper.updateEntityFromDTO(dto, account);
            accountRepo.save(account);
        }
}
