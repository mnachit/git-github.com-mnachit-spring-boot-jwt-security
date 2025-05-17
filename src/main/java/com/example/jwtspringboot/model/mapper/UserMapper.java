package com.example.jwtspringboot.model.mapper;

import com.example.jwtspringboot.model.dtos.UserRequest;
import com.example.jwtspringboot.model.dtos.UserResponse;
import com.example.jwtspringboot.model.entity.User;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFullName(user.getFullName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    public static User toUser(UserRequest userResponse) {
        User user = new User();
        user.setEmail(userResponse.getEmail());
        user.setFullName(userResponse.getFullName());
        user.setPassword(userResponse.getPassword());
        return user;
    }
}
