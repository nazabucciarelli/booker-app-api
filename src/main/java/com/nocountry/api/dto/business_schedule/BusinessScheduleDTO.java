package com.nocountry.api.dto.business_schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessScheduleDTO {
    private Long id;
    private LocalTime fromHour;
    private LocalTime toHour;
}
