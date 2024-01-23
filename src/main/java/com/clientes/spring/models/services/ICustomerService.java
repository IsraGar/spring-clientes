package com.clientes.spring.models.services;

import com.clientes.spring.models.entity.Customer;

import java.util.List;

public interface ICustomerService {
    public List<Customer> findAll();
}
