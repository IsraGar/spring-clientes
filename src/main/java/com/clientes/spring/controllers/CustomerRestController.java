package com.clientes.spring.controllers;

import com.clientes.spring.models.entity.Customer;
import com.clientes.spring.models.services.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        Customer customer = null;
        Map<String, Object> response = new HashMap<>();
        try{
            customer = this.customerService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error during database query ");
            response.put("error", "Error".concat(e.getMessage()).concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(customer == null){
            response.put("message", "The customer with ID: ".concat(id.toString()).concat(" does not exists in database."));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer, BindingResult result){
        Customer newCustomer = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                            .stream()
                                    .map(err -> "Field: " + err.getField() + ", Error: " + err.getDefaultMessage())
                                            .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            newCustomer = this.customerService.save(customer);
        }catch (DataAccessException e){
            response.put("message", "Error during database query ");
            response.put("error", "Error".concat(e.getMessage()).concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Customer added successfully!");
        response.put("customer", newCustomer);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer customer, BindingResult result, @PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();
        Customer currentCustomer = this.customerService.findById(id);
        Customer updatedCustomer = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Field: " + err.getField() + ", Error: " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if(currentCustomer == null){
            response.put("message", "The customer with ID: ".concat(id.toString()).concat(" does not exists in database."));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try{
            currentCustomer.setName(customer.getName());
            currentCustomer.setLastname(customer.getLastname());
            currentCustomer.setEmail(customer.getEmail());

            updatedCustomer = this.customerService.save(currentCustomer);
        }catch (DataAccessException e){
            response.put("message", "Error during database query ");
            response.put("error", "Error".concat(e.getMessage()).concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Customer updated successfully!");
        response.put("customer", updatedCustomer);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();
        Customer customer = this.customerService.findById(id);

        if(customer == null){
            response.put("message", "The customer with ID: ".concat(id.toString()).concat(" does not exists in database."));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try{
            this.customerService.deleteById(id);
        }catch (DataAccessException e){
            response.put("message", "Error during database query ");
            response.put("error", "Error".concat(e.getMessage()).concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Customer deleted successfully!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
