package com.nocountry.api.repository;

import com.nocountry.api.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT DISTINCT e.* " +
            "FROM employees e " +
            "INNER JOIN employees_services es ON es.employees_id = e.id " +
            "INNER JOIN services s ON es.services_id = s.id " +
            "WHERE s.business_id = :businessId",
            countQuery = "SELECT COUNT(DISTINCT e.id) " +
                    "FROM employees e " +
                    "INNER JOIN employees_services es ON es.employees_id = e.id " +
                    "INNER JOIN services s ON es.services_id = s.id " +
                    "WHERE s.business_id = :businessId",
            nativeQuery = true)
    Page<Employee> findByBusinessId(Long businessId, Pageable pageable);

    @Query(value = "SELECT distinct e " +
            "FROM Employee e " +
            "JOIN EmployeesServices es ON e.id = es.employees_id " +
            "JOIN Service s ON es.services_id = s.id " +
            "WHERE s.id = :id ",
            nativeQuery = true)
    List<Employee> findEmployeesByServiceId(Long id);
}
