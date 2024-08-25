package com.codebook.service;

import org.springframework.http.ResponseEntity;

import com.codebook.model.AuthRequest;
import com.codebook.model.AuthResponse;
import com.codebook.model.MyUser;

public interface AuthService {

    public ResponseEntity<AuthResponse>  login(AuthRequest authRequest) throws Exception;

    public ResponseEntity<AuthResponse> register(MyUser myUser) throws Exception;

}
