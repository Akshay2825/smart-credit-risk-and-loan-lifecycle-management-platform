package com.crlm.serviceImpl;

import com.crlm.enums.AccountStatus;
import com.crlm.model.UserAccount;
import com.crlm.repository.UserAccountRepository;
import com.crlm.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        if(userAccountRepository.existsByUsername(userAccount.getUsername())){
            throw new IllegalArgumentException("User already exist");
        }
        userAccount.setStatus(AccountStatus.ACTIVE);
        return userAccountRepository.save(userAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount getByUsername(String userName) {
        return userAccountRepository.findByUsername(userName).orElseThrow(()->new IllegalArgumentException("Usernot found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount getByUserId(UUID userId) {
        return userAccountRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("User not found"));
    }

    @Override
    public void blockUser(UUID userId) {
        UserAccount user= getByUserId(userId);
        user.setStatus(AccountStatus.BLOCKED);

    }

    @Override
    public void activateUser(UUID userId) {
        UserAccount user= getByUserId(userId);
        user.setStatus((AccountStatus.ACTIVE));

    }
}
