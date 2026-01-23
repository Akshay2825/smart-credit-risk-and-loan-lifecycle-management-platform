package com.crlm.serviceImpl;

import com.crlm.model.Customer;
import com.crlm.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public Customer createCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer getCustomerById(UUID customerId) {
        return null;
    }

    @Override
    public Customer getCustomerByPan(String pan) {
        return null;
    }
}
