package com.crlm.model;

import com.crlm.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "customers", indexes = {
        @Index(name = "id_customer_pan", columnList = "pan")
}
)
public class Customer extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserAccount userAccount;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false, unique = true, length = 10)
    private String pan;

    @Column(length = 150)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal monthlyIncome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType;

    @Column
    private Integer employmentYears;
}
