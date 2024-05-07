package com.nocountry.api.controller;

import com.nocountry.api.dto.appointment.AppointmentDTO;
import com.nocountry.api.dto.appointment.AppointmentInfoDTO;
import com.nocountry.api.service.appointment.AppointmentServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/BookerApp")
public class AppointmentController {

    @Autowired
    AppointmentServiceImpl employeesAppointmentsService;

    /**
     * Allows to list all the Appointments by business id on a specific year and month
     *
     * @param businessId ID of the Business
     * @param year       Year of the query
     * @param month      Month of the query
     * @return List of Appointment entities
     */
    @GetMapping("/appointments/{business_id}")
    public ResponseEntity<List<AppointmentDTO>> listByBusinessIdAndYearAndMonth(
            @PathVariable("business_id") Long businessId,
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month
    ) {
        return new ResponseEntity<List<AppointmentDTO>>(employeesAppointmentsService
                .listByServiceIdsAndYearAndMonth(businessId, year, month), HttpStatus.OK);
    }

    /**
     * Allows to create an Appointment
     *
     * @param appointmentInfoDTO AppointmentInfoDTO entity
     * @return Appointment entity
     */
    @PostMapping("/appointments")
    @Transactional
    public ResponseEntity<AppointmentDTO> create(@RequestBody AppointmentInfoDTO appointmentInfoDTO) {
        return new ResponseEntity<AppointmentDTO>(employeesAppointmentsService.create(appointmentInfoDTO),
                HttpStatus.CREATED);
    }

    /**
     * Allows to get a list of Appointments booked by a customer by its ID
     *
     * @param customerId ID of the customer
     * @return List of Appointment entities
     */
    @GetMapping("/appointments/customer/{customer_id}")
    public ResponseEntity<List<AppointmentDTO>> listAllByCustomerId(@PathVariable("customer_id") Long customerId) {
        return new ResponseEntity<List<AppointmentDTO>>(employeesAppointmentsService
                .listAllByCustomerId(customerId), HttpStatus.OK);
    }

    /**
     * Allows to cancel an appointment by its ID
     *
     * @param employeeAppointmentId ID of the appointment
     * @return No Content (204) HTTP Status
     */
    @PatchMapping("/appointments/cancel/{employee_appointment_id}")
    @Transactional
    public ResponseEntity cancelById(@PathVariable("employee_appointment_id") Long employeeAppointmentId) {
        employeesAppointmentsService.cancelById(employeeAppointmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Allows to get the schedules of free appointments of an employee by its ID and the date
     *
     * @param employeeId ID of employee
     * @param serviceId  ID of service
     * @param date       Date of the query
     * @return List of LocalTime
     */
    @GetMapping("/appointments/free_appointments/{employee_id}/service/{service_id}")
    public ResponseEntity<List<LocalTime>> getFreeAppointmentsByCustomerIdAndDate(
            @PathVariable("employee_id") Long employeeId,
            @PathVariable("service_id") Long serviceId,
            @RequestParam("date") LocalDate date) {
        return new ResponseEntity<List<LocalTime>>(employeesAppointmentsService
                .listFreeAppointmentsByEmployeeIdAndServiceIdAndDate(employeeId, serviceId, date), HttpStatus.OK);
    }

}
