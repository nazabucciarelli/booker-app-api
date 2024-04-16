package com.nocountry.api.service.user;

import com.nocountry.api.dto.user.UserDTO;
import com.nocountry.api.dto.user.UserLoginDTO;

public interface UserService {
    UserDTO login(UserLoginDTO userLoginDTO);
}
