package com.crlm.controller;

import com.crlm.dto.UserAccountDto;
import com.crlm.model.UserAccount;
import com.crlm.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping
    public UserAccountDto createUser(@Valid @RequestBody UserAccountDto dto){
        return userAccountService.createUserAccount(dto);
    }

    @GetMapping("/{userId}")
    public UserAccountDto getUserById(@PathVariable UUID userId){
        return userAccountService.getByUserId(userId);
    }

    @GetMapping("/username/{username}")
    public UserAccountDto getByUsername(@PathVariable String username){
        return  userAccountService.getByUsername(username);
    }

    @PatchMapping("/{userId}/block")
    public void blockUser(@PathVariable UUID userId){
        userAccountService.blockUser(userId);
    }

    @PatchMapping("/{userId}/activate")
    public void activateUser(@PathVariable UUID userId){
        userAccountService.activateUser(userId);
    }

}
