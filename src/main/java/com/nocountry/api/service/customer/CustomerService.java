package com.nocountry.api.service.customer;

import com.nocountry.api.dto.auth.AuthenticationDTO;
import com.nocountry.api.dto.customer.CustomerDTO;
import com.nocountry.api.dto.customer.CustomerInfoDTO;
import com.nocountry.api.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    AuthenticationDTO registerCustomer(CustomerInfoDTO customerInfo);

    List<CustomerDTO> listAllCustomersByBusinessId(Long businessId);

    CustomerDTO updateCustomerInfo(CustomerInfoDTO customerInfo);

    CustomerDTO getCustomerById(Long customerId);

    CustomerDTO findCustomerByUserId(Long userId);
}
