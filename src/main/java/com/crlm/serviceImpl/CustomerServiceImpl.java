package com.crlm.serviceImpl;

import com.crlm.dto.CustomerDto;
import com.crlm.exception.BusinessRuleException;
import com.crlm.exception.ResourceNotFoundException;
import com.crlm.model.Customer;
import com.crlm.model.UserAccount;
import com.crlm.repository.CustomerRepository;
import com.crlm.repository.UserAccountRepository;
import com.crlm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {

        if (customerRepository.existsByPan(dto.getPan()))
            throw new BusinessRuleException("Customer with this PAN already exists");

        UserAccount user = userAccountRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (customerRepository.existsByUserAccount(user))
            throw new BusinessRuleException("User already linked to a customer");

        Customer customer = new Customer();
        customer.setUserAccount(user);
        customer.setFullName(dto.getFullName());
        customer.setPan(dto.getPan());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setMonthlyIncome(dto.getMonthlyIncome());
        customer.setEmploymentType(dto.getEmploymentType());
        customer.setEmploymentYears(dto.getEmploymentYears());

        return map(customerRepository.save(customer));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomerById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return map(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomerByPan(String pan) {
        Customer customer = customerRepository.findByPan(pan)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return map(customer);
    }

    private CustomerDto map(Customer c) {
        CustomerDto d = new CustomerDto();
        d.setId(c.getId());
        d.setUserId(c.getUserAccount().getId());
        d.setFullName(c.getFullName());
        d.setPan(c.getPan());
        d.setEmail(c.getEmail());
        d.setPhone(c.getPhone());
        d.setMonthlyIncome(c.getMonthlyIncome());
        d.setEmploymentType(c.getEmploymentType());
        d.setEmploymentYears(c.getEmploymentYears());
        d.setCreatedAt(c.getCreatedAt());
        return d;
    }
}
