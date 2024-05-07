package com.nocountry.api.service.user;

import com.nocountry.api.dto.auth.AuthenticationDTO;
import com.nocountry.api.dto.user.UserLoginDTO;

public interface UserService {
    AuthenticationDTO login(UserLoginDTO userLoginDTO);
}
