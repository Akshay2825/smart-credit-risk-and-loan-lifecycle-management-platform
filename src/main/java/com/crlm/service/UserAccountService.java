package com.crlm.service;

import com.crlm.model.UserAccount;

import java.util.UUID;

public interface UserAccountService {

    UserAccount createUserAccount(UserAccount userAccount);

    UserAccount getByUsername( String userName);

    UserAccount getByUserId(UUID userId);

    void blockUser(UUID userId);

    void activateUser(UUID userId);
}
