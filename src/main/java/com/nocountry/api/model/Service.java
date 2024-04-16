package com.nocountry.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "business_id")
    private Business business;
    @NotNull
    @Size(max = 50)
    private String name;
    @NotNull
    @Size(max = 130)
    private String description;
    @NotNull
    private Double price;
    @NotNull
    private Integer durationMinutes;
    @ManyToMany
    @JoinTable(name = "employees_services",
            joinColumns = @JoinColumn(name = "services_id"),
            inverseJoinColumns = @JoinColumn(name = "employees_id"))
    private List<Employee> employees;

    public Service(Business business, String name, String description, Double price, Integer durationMinutes) {
        this.business = business;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationMinutes = durationMinutes;
    }
}
