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
    public UserAccountDto createUserAccount(UserAccountDto dto) {

        if (userAccountRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        UserAccount user = new UserAccount();
        user.setUsername(dto.getUsername());
        user.setPasswordHash(dto.getPassword());
        user.setRole(dto.getRole());
        user.setStatus(AccountStatus.ACTIVE);

        return map(userAccountRepository.save(user));
    }


    @Override
    @Transactional(readOnly = true)
    public UserAccountDto getByUsername(String userName) {
        UserAccount user = userAccountRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return map(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccountDto getByUserId(UUID userId) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return map(user);
    }

    @Override
    public void blockUser(UUID userId) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setStatus(AccountStatus.BLOCKED);
    }

    @Override
    public void activateUser(UUID userId) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setStatus(AccountStatus.ACTIVE);
    }

    private UserAccountDto map(UserAccount user) {
        UserAccountDto dto = new UserAccountDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
