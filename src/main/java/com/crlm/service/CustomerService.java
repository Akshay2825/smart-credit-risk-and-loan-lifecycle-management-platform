package com.crlm.service;

import com.crlm.model.Customer;

import java.util.UUID;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer getCustomerById(UUID customerId);

    Customer getCustomerByPan(String pan);


}
