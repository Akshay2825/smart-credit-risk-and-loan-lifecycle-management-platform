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
    public Customer createCustomer(CustomerDto dto) {
        if(customerRepository.existsByPan(dto.getPan())){
            throw new BusinessRuleException("Customer with this pan already exist");
        }
        UserAccount user= userAccountRepository.findById(dto.getId())
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        if(customerRepository.existsByUserAccount(user))
            throw new BusinessRuleException("User already exist");

        Customer customer= new Customer();
        customer.setUserAccount(user);
        customer.setFullName(dto.getFullName());
        customer.setPan(dto.getPan());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setMonthlyIncome(dto.getMonthlyIncome());
        customer.setEmploymentType(dto.getEmploymentType());
        customer.setEmploymentYears(dto.getEmploymentYears());

        return customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerByPan(String pan) {
        return customerRepository.findByPan(pan)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not found"));
    }
}
