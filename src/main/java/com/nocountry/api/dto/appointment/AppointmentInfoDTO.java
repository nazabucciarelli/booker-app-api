package com.nocountry.api.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentInfoDTO {
    @NotBlank
    private Long customerId;
    @NotBlank
    private Long employeeId;
    @NotBlank
    private Long serviceId;
    @NotBlank
    private LocalDateTime datetime;
}
