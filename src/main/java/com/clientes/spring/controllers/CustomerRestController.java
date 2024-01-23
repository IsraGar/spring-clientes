package com.clientes.spring.controllers;

import com.clientes.spring.models.entity.Customer;
import com.clientes.spring.models.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("api/customer")
public class CustomerRestController {
    @Autowired
    private ICustomerService customerService;
    @GetMapping("/all")
    public List<Customer> getAllCustomer(){
        return this.customerService.findAll();
    }
}
