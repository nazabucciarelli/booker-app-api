package com.nocountry.api.dto.service;

import com.nocountry.api.model.Business;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInfoDTO {
    @NotBlank
    private Long businessId;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private Double price;
    @NotBlank
    private Integer durationMinutes;
}
