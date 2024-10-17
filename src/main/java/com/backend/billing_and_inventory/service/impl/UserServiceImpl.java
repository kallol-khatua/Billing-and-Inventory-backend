package com.backend.billing_and_inventory.service.impl;

import com.backend.billing_and_inventory.dto.UserDto;
import com.backend.billing_and_inventory.dto.VerifyUserDto;
import com.backend.billing_and_inventory.mapper.UserMapper;
import com.backend.billing_and_inventory.model.User;
import com.backend.billing_and_inventory.repository.UserRepository;
import com.backend.billing_and_inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User newUser = UserMapper.mapToUser(userDto);

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        newUser.setPassword(hashedPassword);
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setEnabled(true);

        User savedUser = userRepository.save(newUser);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public String verifyUser(VerifyUserDto user) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "Failed";
        }
    }
}
