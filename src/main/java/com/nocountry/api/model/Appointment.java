package com.nocountry.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customers_id")
    private Customer customer;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "employees_id")
    private Employee employee;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "services_id")
    private Service service;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime datetime;
    @NotNull
    @Temporal(TemporalType.TIME)
    private LocalTime endTime;
    @NotNull
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean cancelled;

    public Appointment(Customer customer, Employee employee, Service service, LocalDateTime datetime,
                       LocalTime endTime) {
        this.customer = customer;
        this.employee = employee;
        this.service = service;
        this.datetime = datetime;
        this.endTime = endTime;
        this.cancelled = false;
    }
}
