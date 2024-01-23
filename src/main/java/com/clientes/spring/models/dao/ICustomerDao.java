package com.clientes.spring.models.dao;

import com.clientes.spring.models.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICustomerDao extends CrudRepository<Customer, Long> {
}
