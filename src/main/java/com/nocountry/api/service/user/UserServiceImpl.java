package com.nocountry.api.service.user;

import com.nocountry.api.config.JwtService;
import com.nocountry.api.dto.auth.AuthenticationDTO;
import com.nocountry.api.dto.user.UserLoginDTO;
import com.nocountry.api.exception.ResourceNotFoundException;
import com.nocountry.api.model.User;
import com.nocountry.api.repository.IBusinessRepository;
import com.nocountry.api.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IBusinessRepository businessRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    ModelMapper modelMapper;

    public User findByUsernameAndBusinessId(String username, Long businessId) {
        if(!businessRepository.existsById(businessId))
            throw new ResourceNotFoundException("Business with id " + businessId + " not found");
        Optional<User> optionalUser = userRepository.findByUsernameAndBusinessId(username, businessId);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User with username " + username + " not found");
        }
        return optionalUser.get();
    }

    @Override
    public AuthenticationDTO login(UserLoginDTO userLoginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getUsername(),
                        userLoginDTO.getPassword()
                )
        );
        User user = findByUsernameAndBusinessId(userLoginDTO.getUsername(), userLoginDTO.getBusinessId());
        String jwt = jwtService.generateToken(user);
        return AuthenticationDTO
                .builder()
                .token(jwt)
                .build();
    }
}
