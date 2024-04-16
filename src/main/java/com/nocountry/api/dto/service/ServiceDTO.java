package com.nocountry.api.dto.service;

import com.nocountry.api.model.Business;
import com.nocountry.api.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {
    private Long id;
    private Business business;
    private String name;
    private String description;
    private Double price;
    private Integer durationMinutes;
    private List<Employee> employees;
}
