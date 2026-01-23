package com.crlm.serviceImpl;

import com.crlm.model.UserAccount;
import com.crlm.service.UserAccountService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        return null;
    }

    @Override
    public UserAccount getByUsername(String userName) {
        return null;
    }

    @Override
    public UserAccount getByUserId(UUID userId) {
        return null;
    }

    @Override
    public void blockUser(UUID userId) {

    }

    @Override
    public void activateUser(UUID userId) {

    }
}
