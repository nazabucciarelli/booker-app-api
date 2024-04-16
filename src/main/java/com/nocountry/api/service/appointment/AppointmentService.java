package com.nocountry.api.service.appointment;

import com.nocountry.api.dto.appointment.AppointmentDTO;
import com.nocountry.api.dto.appointment.AppointmentInfoDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {
    AppointmentDTO create(AppointmentInfoDTO employeesAppointments);

    List<AppointmentDTO> listByServiceIdsAndYearAndMonth(Long businessId, Integer year, Integer month);

    List<AppointmentDTO> listAllByCustomerId(Long customerId);

    void cancelById(Long id);

    List<LocalTime> listFreeAppointmentsByEmployeeIdAndServiceIdAndDate(Long employeeId,Long serviceId, LocalDate date);

}
