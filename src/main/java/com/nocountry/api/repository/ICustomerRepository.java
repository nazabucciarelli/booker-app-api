package com.nocountry.api.repository;

import com.nocountry.api.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    //Find a customer by username from user entity
    @Query(value = "SELECT c from Customer c " +
            " INNER JOIN User u ON u.id = c.user.id " +
            " WHERE u.username = :username ")
    Customer findCustomerByUsername(String username);

    //Find a customer by email from user entity
    @Query(value = "SELECT c from Customer c " +
            " INNER JOIN User u ON u.id = c.user.id " +
            " WHERE u.email = :email ")
    Customer findCustomerByEmail(String email);

    @Query("SELECT c FROM Customer c INNER JOIN c.user u WHERE u.business.id = :businessId")
    List<Customer> findCustomersByBusinessId(@Param("businessId") Long businessId);

    Optional<Customer> findCustomerByUserId(Long userId);
}
