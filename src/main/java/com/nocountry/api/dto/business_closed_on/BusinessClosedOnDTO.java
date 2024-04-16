package com.nocountry.api.dto.business_closed_on;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessClosedOnDTO {
    private Long id;
    private LocalDate date;
    private LocalTime fromHour;
    private LocalTime toHour;
}
