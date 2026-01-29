package com.crlm.service;

import com.crlm.dto.CustomerDto;
import com.crlm.model.Customer;

import java.util.UUID;

public interface CustomerService {

    Customer createCustomer(CustomerDto dto);

    Customer getCustomerById(UUID customerId);

    Customer getCustomerByPan(String pan);


}
