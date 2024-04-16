package com.nocountry.api.service.employee;

import com.nocountry.api.dto.employee.EmployeeDTO;
import com.nocountry.api.dto.employee.EmployeeInfoDTO;
import com.nocountry.api.dto.employee.SimpleEmployeeDTO;
import com.nocountry.api.exception.ResourceNotFoundException;
import com.nocountry.api.model.Employee;
import com.nocountry.api.repository.IEmployeeRepository;
import com.nocountry.api.repository.IServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;
    @Autowired
    private IServiceRepository serviceRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Allows to create an employee
     * @param employeeInfoDTO EmployeeInfoDTO with the information of the employee to save
     * @return EmployeeDTO entity
     */
    @Override
    public EmployeeDTO create(EmployeeInfoDTO employeeInfoDTO) {
        List<com.nocountry.api.model.Service> services = Arrays
                .stream(employeeInfoDTO.getServicesId())
                .map(id -> {
                    Optional<com.nocountry.api.model.Service> optionalService = serviceRepository.findById(id);
                    if (optionalService.isEmpty()) {
                        throw new ResourceNotFoundException("Service with id " + id + " not found");
                    }
                    return optionalService.get();
                })
                .toList();
        Employee employee = new Employee(employeeInfoDTO.getFirstName(), employeeInfoDTO.getLastName()
                , employeeInfoDTO.getProfession(), employeeInfoDTO.getPicture(), services, employeeInfoDTO.getWorkingDays());
        return modelMapper.map(employeeRepository.save(employee), EmployeeDTO.class);
    }

    /**
     * Allows to get information about an employee by its id
     * @param id ID of the employee
     * @return EmployeeDTO entity
     */
    @Override
    public EmployeeDTO getById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new ResourceNotFoundException("Employee with id " + id + " not found");
        }
        return modelMapper.map(optionalEmployee.get(), EmployeeDTO.class);
    }

    /**
     * Allows to list all the employees by a business ID
     * @param id ID of the business
     * @param page Number of page
     * @param size Size of page
     * @return List of SimpleEmployeeDTO entity
     */
    @Override
    public List<SimpleEmployeeDTO> listEmployeesByBusinessIdPaginated(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeesPage = employeeRepository.findByServices_Business_Id(id, pageable);
        if (employeesPage.isEmpty()) {
            throw new ResourceNotFoundException("No employees found on business with ID " + id);
        }
        return employeesPage.stream().
                map(employee -> modelMapper.map(employee, SimpleEmployeeDTO.class)).
                collect(Collectors.toList());
    }

    /* @Override       pendiente por revisar esto
    public List<EmployeeDTO> listEmployeesByService(Long id) {
        List<Employee> employees = employeeRepository.findEmployeesByServiceId(id);

        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found");
        }

        List<EmployeeDTO> listEmployees = employees.stream().
                map(employee -> modelMapper.map(employee, EmployeeDTO.class)).
                collect(Collectors.toList());

        return listEmployees;
    } */

    /**
     * List all employees by business id for employees page
     *
     * @param id
     * @return
     */
    /*  pendiente por revisar esto
    public List<EmployeeDTO> listEmployeesByBusinessId(Long id) {
        List<Employee> employees = employeeRepository.findEmployeesByBusinessId(id);

        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found");
        }

        List<EmployeeDTO> listEmployees = employees.stream().
                map(employee -> modelMapper.map(employee, EmployeeDTO.class)).
                collect(Collectors.toList());

        return listEmployees;
    }*/

}
