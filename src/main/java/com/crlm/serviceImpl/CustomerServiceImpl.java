package com.crlm.serviceImpl;

import com.crlm.model.Customer;
import com.crlm.repository.CustomerRepository;
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
    @Override
    public Customer createCustomer(Customer customer) {
        if(customerRepository.existsByPan(customer.getPan())){
            throw new IllegalArgumentException("Customer with this pan already exist");
        }
        if(customerRepository.existsByUserAccount(customer.getUserAccount())){
            throw new IllegalArgumentException("User already linked to a customer");
        }

        return customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(()-> new IllegalArgumentException("Customer not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerByPan(String pan) {
        return customerRepository.findByPan(pan).orElseThrow(()-> new IllegalArgumentException("Customer not found"));
    }
}
