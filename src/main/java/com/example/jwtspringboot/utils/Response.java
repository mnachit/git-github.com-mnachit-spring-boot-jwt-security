package com.example.jwtspringboot.utils;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private String message;
    private T result;
    private String error;
    private List<T> errors;
    private int status;
    private Map<String, String> errorMap;
}