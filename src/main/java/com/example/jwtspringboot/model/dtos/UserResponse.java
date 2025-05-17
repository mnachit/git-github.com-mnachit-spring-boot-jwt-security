package com.example.jwtspringboot.model.dtos;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
}
