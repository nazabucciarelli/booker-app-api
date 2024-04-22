package com.nocountry.api.service.employee;

import com.nocountry.api.dto.employee.EmployeeDTO;
import com.nocountry.api.dto.employee.EmployeeInfoDTO;
import com.nocountry.api.dto.employee.SimpleEmployeeDTO;
import com.nocountry.api.model.Employee;
import org.springframework.data.domain.Page;

import java.time.LocalTime;
import java.util.List;

public interface EmployeeService {

    List<SimpleEmployeeDTO> listEmployeesByBusinessIdPaginated(Long businessId, int page, int size);

    EmployeeDTO create(EmployeeInfoDTO employeeInfoDTO);

    EmployeeDTO getById(Long id);

    EmployeeDTO getEmployeeServices(Long employeeId, Long businessId);

    //List<LocalTime> getEmployeeFreeAppointments(Long employeeId); // esto podria ir mas bien en el service de appointments
}
