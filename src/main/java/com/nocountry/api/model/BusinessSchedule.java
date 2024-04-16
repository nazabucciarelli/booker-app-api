package com.nocountry.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "business_schedules")
public class BusinessSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "businesses_id")
    private Business business;
    @Temporal(TemporalType.TIME)
    private LocalTime fromHour;
    @Temporal(TemporalType.TIME)
    private LocalTime toHour;

    public BusinessSchedule(Business business, LocalTime fromHour, LocalTime toHour) {
        this.business = business;
        this.fromHour = fromHour;
        this.toHour = toHour;
    }
}
