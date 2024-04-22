package com.nocountry.api.dto.business_schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime fromHour;
    @NotBlank
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime toHour;
}
