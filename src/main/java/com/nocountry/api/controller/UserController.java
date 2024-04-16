package com.nocountry.api.controller;

import com.nocountry.api.dto.user.UserDTO;
import com.nocountry.api.dto.user.UserLoginDTO;
import com.nocountry.api.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/BookerApp")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Allows the user to log into the application
     * @param userLoginDTO DTO with user information like username, password and business_id
     * @return UserDTO which is an entity like User but with less information
     */
    @PostMapping("/user/login")
    @Transactional
    public ResponseEntity<UserDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<UserDTO>(userService.login(userLoginDTO), HttpStatus.OK);
    }
}
