package com.nocountry.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "businesses")
public class Business implements Workable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 100)
    private String name;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] logo;
    @NotNull
    @Size(max = 100)
    private String address;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;
    @NotNull
    @Size(max = 7)
    private String workingDays;
}
