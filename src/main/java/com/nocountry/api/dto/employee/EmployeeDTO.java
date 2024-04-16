package com.nocountry.api.dto.employee;

import com.nocountry.api.dto.service.SimpleServiceDTO;
import com.nocountry.api.model.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String profession;
    private byte[] picture;
    private List<SimpleServiceDTO> services;
    private String workingDays;
    private Boolean active;
}
