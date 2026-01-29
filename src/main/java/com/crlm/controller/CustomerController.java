package com.crlm.controller;


import com.crlm.dto.CustomerDto;
import com.crlm.model.Customer;
import com.crlm.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody CustomerDto dto){
        return customerService.createCustomer(dto);
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable UUID customerId){
        return customerService.getCustomerById(customerId);
    }

    @GetMapping("/pan/{pan}")
    public Customer getCustomerByPan(@PathVariable String pan){
        return customerService.getCustomerByPan(pan);
    }



}
