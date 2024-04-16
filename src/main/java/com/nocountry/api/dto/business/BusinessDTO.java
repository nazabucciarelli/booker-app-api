package com.nocountry.api.dto.business;

import com.nocountry.api.model.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDTO {
    private String name;
    private byte[] logo;
    private String address;
    private City city;
    private String workingDays;
}
