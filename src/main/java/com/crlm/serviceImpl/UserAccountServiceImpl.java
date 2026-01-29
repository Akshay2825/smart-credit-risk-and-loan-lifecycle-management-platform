package com.crlm.serviceImpl;

import com.crlm.dto.UserAccountDto;
import com.crlm.enums.AccountStatus;
import com.crlm.exception.ResourceNotFoundException;
import com.crlm.exception.UserAlreadyExistsException;
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
    public UserAccount createUserAccount(UserAccountDto dto) {
        if(userAccountRepository.existsByUsername(dto.getUsername())){
            throw new UserAlreadyExistsException("User already exist");
        }
        UserAccount user= new UserAccount();
        user.setUsername(dto.getUsername());
        user.setPasswordHash(dto.getPasswordHash());
        user.setRole(dto.getRole());
        user.setStatus(AccountStatus.ACTIVE);

        return userAccountRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount getByUsername(String userName) {
        return userAccountRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount getByUserId(UUID userId) {
        return userAccountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
