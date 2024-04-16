package com.nocountry.api.dto.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleServiceDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer durationMinutes;
}
