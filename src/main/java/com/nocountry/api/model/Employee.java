package com.nocountry.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 100)
    private String firstName;
    @NotNull
    @Size(max = 100)
    private String lastName;
    @NotNull
    @Size(max = 70)
    private String profession;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] picture;
    @NotNull
    @ManyToMany
    @JoinTable(name = "employees_services",
            joinColumns = @JoinColumn(name = "employees_id"),
            inverseJoinColumns = @JoinColumn(name = "services_id"))
    private List<Service> services;
    @NotNull
    @Size(max = 7)
    private String workingDays;
    @NotNull
    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean active;

    public Employee(String firstName, String lastName, String profession, byte[] picture, List<Service> services,
                    String workingDays) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profession = profession;
        this.picture = picture;
        this.services = services;
        this.workingDays = workingDays;
        this.active = true;
    }
}
