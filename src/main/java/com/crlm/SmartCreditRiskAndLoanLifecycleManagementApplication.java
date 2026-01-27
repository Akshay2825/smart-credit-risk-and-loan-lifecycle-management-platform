package com.crlm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmartCreditRiskAndLoanLifecycleManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartCreditRiskAndLoanLifecycleManagementApplication.class, args);
	}

}
