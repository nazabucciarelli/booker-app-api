package com.nocountry.api.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInfoDTO {
    private String firstName;
    private String lastName;
    private String profession;
    private byte[] picture;
    private Long[] servicesId;
    private String workingDays;
}
