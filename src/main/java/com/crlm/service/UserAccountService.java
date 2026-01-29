package com.crlm.service;

import com.crlm.dto.UserAccountDto;
import com.crlm.model.UserAccount;

import java.util.UUID;

public interface UserAccountService {

    UserAccountDto createUserAccount(UserAccountDto dto);

    UserAccountDto getByUsername( String userName);

    UserAccountDto getByUserId(UUID userId);

    void blockUser(UUID userId);

    void activateUser(UUID userId);
}
