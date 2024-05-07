package com.nocountry.api.controller;

import com.nocountry.api.dto.auth.AuthenticationDTO;
import com.nocountry.api.dto.customer.CustomerDTO;
import com.nocountry.api.dto.customer.CustomerInfoDTO;
import com.nocountry.api.service.customer.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BookerApp")
public class CustomerController {

    @Autowired
    CustomerService customerServiceImpl;

    /**
     * Allows to register customer in database
     *
     * @param customerInfo CustomerInfoDTO entity
     * @return Customer information
     */
    @PostMapping
    @Transactional
    @RequestMapping("/customer/register")
    public ResponseEntity<AuthenticationDTO> registerCustomer(@RequestBody CustomerInfoDTO customerInfo) {
        return new ResponseEntity<AuthenticationDTO>(customerServiceImpl.registerCustomer(customerInfo),
                HttpStatus.CREATED);
    }

    /**
     * Get a list of all customers info
     *
     * @return List of customers
     */
    @GetMapping("/customers/{business_id}")
    public ResponseEntity<List<CustomerDTO>> listAllCustomersByBusinessId(@PathVariable("business_id") Long businessId) {
        return new ResponseEntity<List<CustomerDTO>>(customerServiceImpl.listAllCustomersByBusinessId(businessId),
                HttpStatus.OK);
    }

    /**
     * Update customer by id, it requires a customer info data from request body
     *
     * @param customerInfo CustomerInfoDTO
     * @return customer update info
     */
    @PutMapping("/customer")
    @Transactional
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerInfoDTO customerInfo) {
        return new ResponseEntity<CustomerDTO>(customerServiceImpl.updateCustomerInfo(customerInfo), HttpStatus.OK);
    }

    /**
     * Allows to get information of a customer by its ID
     *
     * @param customerId ID of Customer
     * @return Customer information
     */
    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("customer_id") Long customerId) {
        return new ResponseEntity<CustomerDTO>(customerServiceImpl.getCustomerById(customerId), HttpStatus.OK);
    }

    /**
     * Allows to get information of a customer by its User ID
     * @param userId ID of User
     * @return Customer information
     */
    @GetMapping("/customer/user/{user_id}")
    public ResponseEntity<CustomerDTO> getCustomerByUserId(@PathVariable("user_id") Long userId) {
        return new ResponseEntity<CustomerDTO>(customerServiceImpl.findCustomerByUserId(userId),HttpStatus.OK);
    }
}
