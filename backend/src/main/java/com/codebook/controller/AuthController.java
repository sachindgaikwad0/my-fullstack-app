package com.codebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codebook.model.AuthRequest;
import com.codebook.model.AuthResponse;
import com.codebook.model.MyUser;
import com.codebook.service.AuthService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            System.out.println("login authRequest::" + authRequest);
            return authService.login(authRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Invalid username or password", e);
        }
    }

    @PostMapping(path = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody MyUser myUser) throws Exception {

        System.out.println("register myUser::" + myUser);
        return authService.register(myUser);
    }

    @GetMapping(path="/admin")
    public String adminTest(){
        return "admin test";
    }

    @GetMapping(path="/user")
    public String userTest(){
        return "user test";
    }
}
