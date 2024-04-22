package com.nocountry.api.dto.business_schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime fromHour;
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime toHour;
}
