package com.crlm.dto;

import com.crlm.enums.EmploymentType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CustomerDto {

    @NotNull
    private UUID id;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(min = 10, max = 10)
    private String pan;

    @Email
    private String email;

    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal monthlyIncome;

    @NotNull
    private EmploymentType employmentType;

    @Min(0)
    private Integer employmentYears;
}
