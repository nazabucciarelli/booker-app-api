package com.nocountry.api.service.customer;

import com.nocountry.api.dto.customer.CustomerDTO;
import com.nocountry.api.dto.customer.CustomerInfoDTO;
import com.nocountry.api.exception.EntityUpdateException;
import com.nocountry.api.exception.RegisterException;
import com.nocountry.api.exception.ResourceNotFoundException;
import com.nocountry.api.model.Business;
import com.nocountry.api.model.Customer;
import com.nocountry.api.model.Role;
import com.nocountry.api.model.User;
import com.nocountry.api.repository.IBusinessRepository;
import com.nocountry.api.repository.ICustomerRepository;
import com.nocountry.api.repository.IRoleRepository;
import com.nocountry.api.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    ICustomerRepository customerRepository;
    @Autowired
    IRoleRepository roleRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IBusinessRepository businessRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CustomerDTO registerCustomer(CustomerInfoDTO customerInfo) {
        if(userRepository.existsByEmail(customerInfo.getEmail())){
            throw new RegisterException("Customer with email " + customerInfo.getEmail() + " already exists");
        } else if(userRepository.existsByUsername(customerInfo.getUsername())){
            throw new RegisterException("Customer with username " + customerInfo.getUsername() + " already exists");
        }
        Optional<Role> optionalRole = roleRepository.findByName("USER");
        Optional<Business> optionalBusiness = businessRepository.findById(customerInfo.getBusinessId());
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            if (optionalBusiness.isEmpty()) {
                throw new ResourceNotFoundException("Business with id " + customerInfo.getBusinessId() + " does not exist");
            }
            User user = new User(customerInfo.getUsername(), customerInfo.getPassword(), customerInfo.getEmail(), role,
                    optionalBusiness.get());
            userRepository.save(user);

            Customer customer = new Customer(user, customerInfo.getFirstName(), customerInfo.getLastName(), customerInfo.getPhone());
            customer = customerRepository.save(customer);
            return modelMapper.map(customer, CustomerDTO.class);
        } else {
            throw new ResourceNotFoundException("Role with name " + customerInfo.getRoleName() + " not found");
        }
    }

    @Override
    public List<CustomerDTO> listAllCustomersByBusinessId(Long businessId) {
        try {
            List<Customer> listCustomers = customerRepository.findCustomersByBusinessId(businessId);
            List<CustomerDTO> customers = listCustomers.
                    stream().
                    map(customer -> modelMapper.map(customer, CustomerDTO.class)).
                    collect(Collectors.toList());

            return customers;
        } catch (ResourceNotFoundException rnf) {
            throw new ResourceNotFoundException(rnf.getMessage());
        }

    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        try{
            Optional<Customer> customer = customerRepository.findById(id);

            if (customer.isPresent())
                return modelMapper.map(customer.get(), CustomerDTO.class);
        } catch (ResourceNotFoundException rnf) {
            throw new ResourceNotFoundException(rnf.getMessage());
        }

        return null;
    }

    @Override
    public CustomerDTO findCustomerByUserId(Long userId) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByUserId(userId);
        if (optionalCustomer.isEmpty()){
            throw new ResourceNotFoundException("Customer with User ID " + userId + " not found");
        }
        return modelMapper.map(optionalCustomer.get(), CustomerDTO.class);
    }

    private Customer getCustomerByEmail(String email) {
        try{
            Customer customer = customerRepository.findCustomerByEmail(email);

            if (customer != null)
                return customer;
        } catch (ResourceNotFoundException rnf) {
            throw new ResourceNotFoundException(rnf.getMessage());
        }

        return null;
    }

    private Customer getCustomerByUsername(String username) {
        try{
            Customer customer = customerRepository.findCustomerByUsername(username);

            if (customer != null)
                return customer;
        } catch (ResourceNotFoundException rnf) {
            throw new ResourceNotFoundException(rnf.getMessage());
        }

        return null;
    }

    @Override
    public CustomerDTO updateCustomerInfo(CustomerInfoDTO customerInfo) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerInfo.getId());
        Customer customerByEmail = getCustomerByEmail(customerInfo.getEmail());
        Customer customerByUsername = getCustomerByUsername(customerInfo.getUsername());
        if (optionalCustomer.isEmpty()) {
            throw new ResourceNotFoundException("Customer with id " + customerInfo.getId() + " does not exist");
        } else if (customerByEmail != null && !customerByEmail.getId().equals(customerInfo.getId())) {
            throw new EntityUpdateException("Customer with email " + customerInfo.getEmail() + " already exists");
        } else if ( customerByUsername != null && !customerByUsername.getId().equals(customerInfo.getId())) {
            throw new EntityUpdateException("Customer with username " + customerInfo.getUsername() + " already exists");
        }
        Customer customer = optionalCustomer.get();
        customer.setFirstName(customerInfo.getFirstName());
        customer.setLastName(customerInfo.getLastName());
        customer.setPhone(customerInfo.getPhone());
        User user = customer.getUser();
        user.setUsername(customerInfo.getUsername());
        user.setPassword(customerInfo.getPassword());
        user.setEmail(customerInfo.getEmail());
        return modelMapper.map(customer, CustomerDTO.class);
    }

}
