package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.UserEntity;
import com.gymbro.GymBro.web.DTO.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    UserEntity findUserByName(String name);

    List<UserDto> findAllUsers();
}
