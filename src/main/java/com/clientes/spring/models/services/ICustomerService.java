package com.clientes.spring.models.services;

import com.clientes.spring.models.entity.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICustomerService {
    public List<Customer> findAll();

    @Transactional
    public Customer save(Customer customer);

    @Transactional
    public void deleteById(Long id);

    @Transactional(readOnly = true)
    public Customer findById(Long id);
}
