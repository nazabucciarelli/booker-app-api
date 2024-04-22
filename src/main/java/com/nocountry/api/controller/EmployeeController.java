package com.nocountry.api.controller;

import com.nocountry.api.dto.employee.EmployeeDTO;
import com.nocountry.api.dto.employee.EmployeeInfoDTO;
import com.nocountry.api.dto.employee.SimpleEmployeeDTO;
import com.nocountry.api.model.Employee;
import com.nocountry.api.service.employee.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BookerApp")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employees/business/{business_id}")
    public ResponseEntity<List<SimpleEmployeeDTO>> getEmployeesByBusinessIdPaginated(
            @PathVariable("business_id") Long businessId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        return new ResponseEntity<List<SimpleEmployeeDTO>>(employeeService.listEmployeesByBusinessIdPaginated(businessId,
                page, size), HttpStatus.OK);
    }

    @PostMapping("/employees")
    @Transactional
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeInfoDTO employeeInfoDTO) {
        return new ResponseEntity<EmployeeDTO>(employeeService.create(employeeInfoDTO),HttpStatus.CREATED);
    }

    @GetMapping("/employees/{employee_id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("employee_id") Long employeeId) {
        return new ResponseEntity<EmployeeDTO>(employeeService.getById(employeeId),HttpStatus.OK);
    }

    @GetMapping("/employees/services")
    public ResponseEntity<EmployeeDTO> getEmployeeServices(
            @RequestParam("employee_id") Long employeeId,
            @RequestParam("business_id") Long businessId){
        return new ResponseEntity<EmployeeDTO>(employeeService.getEmployeeServices(employeeId, businessId), HttpStatus.OK);
    }

}
