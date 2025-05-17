package com.example.jwtspringboot.controller;

import com.example.jwtspringboot.config.JwtUtil;
import com.example.jwtspringboot.model.dtos.UserRequest;
import com.example.jwtspringboot.model.entity.User;
import com.example.jwtspringboot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.jwtspringboot.utils.Response;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private JwtUtil jwtUtil;
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        Response response = new Response();
        userService.findByEmailAndPassword(userRequest.getEmail(), userRequest.getPassword());

        User user = userService.findByEmail(userRequest.getEmail());
        String token = jwtUtil.createToken(user);
        response.setResult(token);
        response.setMessage("Success");
        response.setStatus(200);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        Response response = new Response();
        userService.save(userRequest);
        response.setMessage("Success");
        response.setStatus(200);
        return ResponseEntity.ok(response);
    }
}
