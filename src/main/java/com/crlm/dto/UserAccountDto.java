package com.crlm.dto;

import com.crlm.enums.AccountStatus;
import com.crlm.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class UserAccountDto {

    // Response fields
    private UUID id;
    private AccountStatus status;
    private Instant createdAt;

    // Request fields
    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;   // <-- plain password

    @NotNull
    private UserRole role;
}
