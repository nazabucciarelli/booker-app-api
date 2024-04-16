package com.nocountry.api.dto.customer;

import com.nocountry.api.dto.user.UserDTO;
import com.nocountry.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private UserDTO user;
    private String firstName;
    private String lastName;
    private String phone;
}
