package com.crlm.service;

import com.crlm.dto.CustomerDto;
import com.crlm.model.Customer;

import java.util.UUID;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto dto);

    CustomerDto getCustomerById(UUID customerId);

    CustomerDto getCustomerByPan(String pan);
}
