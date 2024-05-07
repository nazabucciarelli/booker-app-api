package com.nocountry.api.service.appointment;

import com.nocountry.api.dto.appointment.AppointmentDTO;
import com.nocountry.api.dto.appointment.AppointmentInfoDTO;
import com.nocountry.api.exception.AppointmentException;
import com.nocountry.api.exception.ResourceNotFoundException;
import com.nocountry.api.model.*;
import com.nocountry.api.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@org.springframework.stereotype.Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    IAppointmentRepository appointmentRepository;

    @Autowired
    IEmployeeRepository employeeRepository;

    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    IServiceRepository serviceRepository;

    @Autowired
    IBusinessRepository businessRepository;

    @Autowired
    IEmployeeScheduleRepository employeeScheduleRepository;

    @Autowired
    IBusinessClosedOnRepository businessClosedOnRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppointmentDTO create(AppointmentInfoDTO appointmentInfoDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(appointmentInfoDTO.getEmployeeId());
        Optional<Customer> optionalCustomer = customerRepository.findById(appointmentInfoDTO.getCustomerId());
        Optional<Service> optionalService = serviceRepository.findById(appointmentInfoDTO.getServiceId());
        if (optionalEmployee.isEmpty()) {
            throw new ResourceNotFoundException("Employee with ID " + appointmentInfoDTO.getEmployeeId() + " not found");
        } else if (optionalCustomer.isEmpty()) {
            throw new ResourceNotFoundException("Customer with ID " + appointmentInfoDTO.getCustomerId() + " not found");
        } else if (optionalService.isEmpty()) {
            throw new ResourceNotFoundException("Service with ID " + appointmentInfoDTO.getServiceId() + " not found");
        }
        Employee employee = optionalEmployee.get();
        Service service = optionalService.get();
        if (!employee.getServices().contains(service)) {
            throw new AppointmentException("Employee with ID " + appointmentInfoDTO.getEmployeeId()
                    + " doesn't provide a Service with ID " + service.getId());
        }
        LocalTime startTime = appointmentInfoDTO.getDatetime().toLocalTime();
        LocalTime endTime = appointmentInfoDTO.getDatetime().plusMinutes(service.getDurationMinutes()).toLocalTime();
        List<Appointment> employeesAppointments = appointmentRepository
                .findByEmployeeIdAndDate(employee.getId(), appointmentInfoDTO.getDatetime().toLocalDate());
        for (Appointment ea : employeesAppointments) {
            if (ea.getCancelled()) {
                continue;
            }
            LocalTime eaStartTime = ea.getDatetime().toLocalTime();
            boolean isStartTimeWithinReservedInterval = (startTime.isAfter(eaStartTime) ||
                    startTime.equals(eaStartTime)) &&
                    startTime.isBefore(ea.getEndTime());
            boolean isEndTimeWithinReservedInterval = (endTime.isAfter(eaStartTime)) &&
                    startTime.isBefore(ea.getEndTime());
            if (isStartTimeWithinReservedInterval || isEndTimeWithinReservedInterval) {
                throw new AppointmentException("The interval between start time and end time of the appointment" +
                        " is within a reserved interval of time");
            }
        }
        Appointment employeesAppointment = appointmentRepository.save(new Appointment(optionalCustomer.get(), employee,
                service, appointmentInfoDTO.getDatetime(), endTime));
        return modelMapper.map(employeesAppointment, AppointmentDTO.class);
    }

    @Override
    public List<AppointmentDTO> listByServiceIdsAndYearAndMonth(Long businessId, Integer year, Integer month) {
        if (!businessRepository.existsById(businessId)) {
            throw new ResourceNotFoundException("Business with ID " + businessId + " not found");
        }
        List<Service> serviceList = serviceRepository.findAllByBusinessId(businessId);
        List<Appointment> appointmentList = appointmentRepository
                .findByServiceIdsAndYearAndMonth(serviceList
                        .stream()
                        .map((Service::getId))
                        .toList(), year, month);
        if (appointmentList.isEmpty()) {
            throw new ResourceNotFoundException("Business with ID " + businessId + " has no appointments on " + year +
                    "/" + month + " yet");
        }
        return appointmentList.stream().map(employeesAppointment ->
                modelMapper.map(employeesAppointment, AppointmentDTO.class)
        ).toList();
    }

    @Override
    public List<AppointmentDTO> listAllByCustomerId(Long customerId) {
        boolean customerExist = customerRepository.existsById(customerId);
        if (!customerExist) {
            throw new ResourceNotFoundException("Customer with ID " + customerId + " not found");
        }
        List<Appointment> appointmentList = appointmentRepository
                .findByCustomerId(customerId);
        if (appointmentList.isEmpty()) {
            throw new ResourceNotFoundException("Customer with ID " + customerId + " has no appointments yet");
        }
        return appointmentList.stream()
                .map(employeesAppointment -> modelMapper.map(employeesAppointment, AppointmentDTO.class))
                .toList();
    }

    @Override
    public void cancelById(Long id) {
        Optional<Appointment> optionalEmployeesAppointments = appointmentRepository.findById(id);
        Appointment appointment;
        if (optionalEmployeesAppointments.isPresent()) {
            appointment = optionalEmployeesAppointments.get();
            appointment.setCancelled(true);
        } else {
            throw new ResourceNotFoundException("Appointment with ID " + id + " not found");
        }
        appointmentRepository.save(appointment);
    }

    @Override
    public List<LocalTime> listFreeAppointmentsByEmployeeIdAndServiceIdAndDate(Long employeeId, Long serviceId,
                                                                               LocalDate date) {
        final int INTERVAL_MINUTES = 15;

        List<LocalTime> freeAppointments = new ArrayList<>();
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Optional<Service> optionalService = serviceRepository.findById(serviceId);
        if (optionalEmployee.isEmpty()) {
            throw new ResourceNotFoundException("Employee with ID " + employeeId + " not found");
        } else if (optionalService.isEmpty()) {
            throw new ResourceNotFoundException("Service with ID " + serviceId + " not found");
        }
        Employee employee = optionalEmployee.get();
        Service service = optionalService.get();
        if (!employee.getServices().contains(service)) {
            throw new AppointmentException("Employee with ID " + employeeId
                    + " doesn't provide a Service with ID " + service.getId());
        }

        Optional<BusinessClosedOn> optionalBusinessClosedOn = businessClosedOnRepository
                .findByDateAndBusinessId(date, service.getBusiness().getId());
        BusinessClosedOn businessClosedOn = null;
        if (optionalBusinessClosedOn.isPresent()) {
            businessClosedOn = optionalBusinessClosedOn.get();
            if (businessClosedOn.getToHour() == null && businessClosedOn.getFromHour() == null) {
                return List.of();
            }
        }

        if (getWorkingDaysListOfWorkable(employee).contains(date.getDayOfWeek()) &&
                getWorkingDaysListOfWorkable(service.getBusiness()).contains(date.getDayOfWeek())) {
            List<Appointment> appointmentList = appointmentRepository.findByEmployeeIdAndDate(employee.getId(), date);
            Map<LocalTime, LocalTime> reservedAppointments = new HashMap<>();
            for (Appointment a : appointmentList) {
                reservedAppointments.put(a.getDatetime().toLocalTime(), a.getEndTime());
            }
            List<EmployeeSchedule> employeeSchedules = employeeScheduleRepository.findByEmployeeId(employeeId);
            if (employeeSchedules.isEmpty()) {
                throw new AppointmentException("Employee with ID " + employeeId + " has no schedules yet");
            }

            for (EmployeeSchedule es : employeeSchedules) {
                LocalTime fromHour = es.getFromHour();
                LocalTime toHour = es.getToHour();
                while (fromHour.isBefore(toHour)) {
                    boolean collide = false;
                    for (Map.Entry<LocalTime, LocalTime> entry : reservedAppointments.entrySet()) {
                        LocalTime appointmentFromHour = entry.getKey();
                        LocalTime appointmentToHour = entry.getValue();
                        if (fromHour.equals(appointmentFromHour) ||
                                (fromHour.isAfter(appointmentFromHour) &&
                                        fromHour.isBefore(appointmentToHour)) ||
                                (fromHour.plusMinutes(service.getDurationMinutes()).isAfter(appointmentFromHour)
                                        && fromHour.isBefore(appointmentFromHour))) {
                            collide = true;
                            break;
                        }
                    }
                    if (!collide && !fromHour.plusMinutes(service.getDurationMinutes()).isAfter(toHour)) {
                        if ((businessClosedOn != null &&
                                !(fromHour.isAfter(businessClosedOn.getFromHour()) &&
                                        fromHour.isBefore(businessClosedOn.getToHour()))
                                && !(fromHour.plusMinutes(service.getDurationMinutes())
                                .isAfter(businessClosedOn.getFromHour()) &&
                                fromHour.plusMinutes(service.getDurationMinutes()).isBefore(businessClosedOn.getToHour()))
                        )) {
                            freeAppointments.add(fromHour);
                        } else if (businessClosedOn == null) {
                            freeAppointments.add(fromHour);
                        }
                    }
                    fromHour = fromHour.plusMinutes(INTERVAL_MINUTES);
                }
            }
            return freeAppointments;
        }
        return List.of();
    }

    private List<DayOfWeek> getWorkingDaysListOfWorkable(Workable workable) {
        List<DayOfWeek> workingDaysList = new ArrayList<>();
        for (char letter : workable.getWorkingDays().toCharArray()) {
            switch (letter) {
                case 'L':
                    workingDaysList.add(DayOfWeek.MONDAY);
                    break;
                case 'M':
                    workingDaysList.add(DayOfWeek.TUESDAY);
                    break;
                case 'X':
                    workingDaysList.add(DayOfWeek.WEDNESDAY);
                    break;
                case 'J':
                    workingDaysList.add(DayOfWeek.THURSDAY);
                    break;
                case 'V':
                    workingDaysList.add(DayOfWeek.FRIDAY);
                    break;
                case 'S':
                    workingDaysList.add(DayOfWeek.SATURDAY);
                    break;
                case 'D':
                    workingDaysList.add(DayOfWeek.SUNDAY);
                    break;
                default:
                    break;
            }
        }
        return workingDaysList;
    }
}
