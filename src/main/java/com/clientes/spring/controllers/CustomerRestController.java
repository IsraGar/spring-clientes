package com.clientes.spring.controllers;

import com.clientes.spring.models.entity.Customer;
import com.clientes.spring.models.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("api/customers/")
public class CustomerRestController {
    @Autowired
    private ICustomerService customerService;
    @GetMapping("list")
    public List<Customer> getAllCustomer(){
        return this.customerService.findAll();
    }

    @GetMapping("list/{id}")
    public Customer findById(@PathVariable("id") Long id){
        return this.customerService.findById(id);
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer){
        return this.customerService.save(customer);
    }

    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable("id") Long id){
        Customer currentCustomer = this.customerService.findById(id);
        currentCustomer.setName(customer.getName());
        currentCustomer.setLastname(customer.getLastname());
        currentCustomer.setEmail(customer.getEmail());
        return this.customerService.save(currentCustomer);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Long id){
        this.customerService.deleteById(id);
    }
}
