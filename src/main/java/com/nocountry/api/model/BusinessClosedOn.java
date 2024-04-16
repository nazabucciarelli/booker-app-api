package com.nocountry.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Entity
@Table(name = "business_closed_on")
public class BusinessClosedOn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate date;
    @Temporal(TemporalType.TIME)
    private LocalTime fromHour;
    @Temporal(TemporalType.TIME)
    private LocalTime toHour;

    public BusinessClosedOn(Business business, LocalDate date, LocalTime fromHour, LocalTime toHour) {
        this.business = business;
        this.date = date;
        this.fromHour = fromHour;
        this.toHour = toHour;
    }
}
