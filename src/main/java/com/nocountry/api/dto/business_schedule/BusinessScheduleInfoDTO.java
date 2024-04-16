package com.nocountry.api.dto.business_schedule;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessScheduleInfoDTO {
    @NotBlank
    private Long businessId;
    @NotBlank
    private LocalTime fromHour;
    @NotBlank
    private LocalTime toHour;
}
