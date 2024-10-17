package com.backend.billing_and_inventory.service;

import com.backend.billing_and_inventory.dto.UserDto;
import com.backend.billing_and_inventory.dto.VerifyUserDto;

public interface UserService {
    UserDto registerUser(UserDto userDto);
    String verifyUser(VerifyUserDto user);
}