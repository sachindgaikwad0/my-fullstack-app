package com.codebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.codebook.model.AuthRequest;
import com.codebook.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private JwtUtil  jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public String login(AuthRequest authRequest) throws Exception {
        // Authenticate the user
        try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        System.out.println("reached"+authRequest);
        final UserDetails  userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails.getUsername());
        return token;
        }catch (AuthenticationException e) {
            throw new Exception("Invalid Username or password",e);
        }
    }

}
