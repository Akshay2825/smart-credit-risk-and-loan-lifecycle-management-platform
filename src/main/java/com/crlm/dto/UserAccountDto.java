package com.crlm.dto;


import com.crlm.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountDto {

    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(min = 8)
    private String passwordHash;

    @NotNull
    private UserRole role;


}
