package com.example.jwtspringboot.service.impl;

import com.example.jwtspringboot.exception.ValidationException;
import com.example.jwtspringboot.model.dtos.UserRequest;
import com.example.jwtspringboot.model.entity.User;
import com.example.jwtspringboot.model.mapper.UserMapper;
import com.example.jwtspringboot.repository.UserRepository;
import com.example.jwtspringboot.service.UserService;
import com.example.jwtspringboot.utils.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean findByEmailAndPassword(String email, String password) throws ValidationException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        List<ErrorMessage> errorMessages = new ArrayList<>();

        if (userOptional.isEmpty()) {
            errorMessages.add(ErrorMessage.builder().message("Email is incorrect").build());
            throw new ValidationException(errorMessages);
        }

        User user = userOptional.get();
        if (!isPasswordValid(password, user.getPassword())) {
            errorMessages.add(ErrorMessage.builder().message("Password is incorrect").build());
            throw new ValidationException(errorMessages);
        }

        return true;
    }

    @Override
    public User findByEmail(String email) throws ValidationException{
        if (userRepository.findByEmail(email).isEmpty()) {
            throw new ValidationException(List.of(ErrorMessage.builder().message("Email not found").build()));
        }

        return userRepository.findByEmail(email).get();
    }

    @Override
    public boolean save(UserRequest user) throws ValidationException {
        List<ErrorMessage> errorMessages = new ArrayList<>();
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            errorMessages.add(ErrorMessage.builder().message("Email already exists").build());
        }
        if (user.getPassword().length() < 8) {
            errorMessages.add(ErrorMessage.builder().message("Password must be at least 8 characters long").build());
        }
        if (errorMessages.size() > 0) {
            throw new ValidationException(errorMessages);
        }

        userRepository.save(UserMapper.toUser(user));
        return true;
    }

    public boolean isPasswordValid(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
