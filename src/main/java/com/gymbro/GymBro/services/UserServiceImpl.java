package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.UserEntity;
import com.gymbro.GymBro.repositories.UserRepository;
import com.gymbro.GymBro.web.DTO.UserDto;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
    }

    @Override
    public UserEntity findUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserEntity findUserById(Long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        return userEntityOptional.orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

    private UserDto mapToUserDto(UserEntity user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        return userDto;
    }
}
