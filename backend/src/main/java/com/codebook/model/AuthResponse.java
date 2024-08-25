package com.codebook.model;

import java.util.List;

import lombok.Data;

@Data
public class AuthResponse {
    private String username;
    private List<String> roles;
    private String token;
}
