package com.nocountry.api.service.user;

import com.nocountry.api.dto.user.UserDTO;
import com.nocountry.api.dto.user.UserLoginDTO;
import com.nocountry.api.exception.LoginException;
import com.nocountry.api.exception.ResourceNotFoundException;
import com.nocountry.api.model.User;
import com.nocountry.api.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    public User findByUsernameAndBusinessId(String username, Long businessId) {
        Optional<User> optionalUser = userRepository.findByUsernameAndBusinessId(username,businessId);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User with username " + username + " not found");
        }
        return optionalUser.get();
    }


    @Override
    public UserDTO login(UserLoginDTO userLoginDTO) {
        User user = findByUsernameAndBusinessId(userLoginDTO.getUsername(), userLoginDTO.getBusinessId());
        if (user.getPassword().equals(userLoginDTO.getPassword())){
            return modelMapper.map(user, UserDTO.class);
        };
        throw new LoginException("Invalid password for user with username " + userLoginDTO.getUsername());
    }
}
