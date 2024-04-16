package com.nocountry.api.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerInfoDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
    private String password;
    private String email;
    private Long businessId;
}
