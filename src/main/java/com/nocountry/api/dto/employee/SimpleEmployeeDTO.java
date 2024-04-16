package com.nocountry.api.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleEmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String profession;
    private String picture;
}
