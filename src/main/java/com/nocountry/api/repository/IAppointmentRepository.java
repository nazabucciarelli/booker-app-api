package com.nocountry.api.repository;

import com.nocountry.api.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(value = "SELECT ea " +
            " FROM Appointment ea " +
            " WHERE ea.service.id IN :serviceIds " +
            " AND MONTH(ea.datetime) = :month AND YEAR(ea.datetime) = :year ")
    List<Appointment> findByServiceIdsAndYearAndMonth(List<Long> serviceIds, int year, int month);

    @Query(value = "SELECT ea " +
            " FROM Appointment ea " +
            " WHERE ea.service.id IN :serviceIds AND ea.customer.id = :customerId")
    List<Appointment> findByCustomerIdAndServiceIds(List<Long> serviceIds, Long customerId);

    @Query(value = "SELECT ea " +
            " FROM Appointment ea " +
            " WHERE ea.employee.id = :employeeId " +
            " AND DATE(ea.datetime) = :date AND ea.cancelled = false")
    List<Appointment> findByEmployeeIdAndDate(Long employeeId, LocalDate date);

    List<Appointment> findByCustomerId(Long customerId);
}
