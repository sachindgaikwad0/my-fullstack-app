package com.codebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codebook.model.AuthRequest;
import com.codebook.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/authenticate")
    public String login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            System.out.println("login authRequest::"+authRequest);
            return authService.login(authRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Invalid username or password", e);
        }
    }

}
