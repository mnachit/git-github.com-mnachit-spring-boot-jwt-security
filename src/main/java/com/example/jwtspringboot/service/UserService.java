package com.example.jwtspringboot.service;

import com.example.jwtspringboot.exception.ValidationException;
import com.example.jwtspringboot.model.dtos.UserRequest;
import com.example.jwtspringboot.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public boolean findByEmailAndPassword(String email, String password) throws ValidationException;
    public User findByEmail(String email) throws ValidationException;
    public boolean save(UserRequest user) throws ValidationException;



}
