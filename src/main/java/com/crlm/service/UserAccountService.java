package com.crlm.service;

import com.crlm.dto.UserAccountDto;
import com.crlm.model.UserAccount;

import java.util.UUID;

public interface UserAccountService {

    UserAccount createUserAccount(UserAccountDto dto);

    UserAccount getByUsername( String userName);

    UserAccount getByUserId(UUID userId);

    void blockUser(UUID userId);

    void activateUser(UUID userId);
}
