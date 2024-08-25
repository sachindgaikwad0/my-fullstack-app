package com.codebook.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codebook.model.AuthRequest;
import com.codebook.model.AuthResponse;
import com.codebook.model.MyUser;
import com.codebook.repository.MyUserRepo;
import com.codebook.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyUserRepo myUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) throws Exception {
        // Authenticate the user
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            System.out.println("reached" + authRequest);
            AuthResponse authResponse = new AuthResponse();
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

            final String token = jwtUtil.generateToken(userDetails.getUsername());
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            List<String> roles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(role -> role.replace("ROLE_", ""))
                    .collect(Collectors.toList());
            authResponse.setRoles(roles);
            authResponse.setUsername(userDetails.getUsername());
            authResponse.setToken(token);

            return ResponseEntity.ok(authResponse);
        } catch (AuthenticationException e) {
            throw new Exception("Invalid Username or password", e);
        }
    }

    @Override
    public ResponseEntity<AuthResponse> register(MyUser myUser) throws Exception {
        if (validateUser(myUser)) {
            // throw new Exception("User already exist");
            return ResponseEntity.badRequest().build();
        } else {
            myUser.setRoles("USER");
            myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
            myUserRepo.save(myUser);
            final String token = jwtUtil.generateToken(myUser.getEmail());
            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(token);
            authResponse.setUsername(myUser.getEmail());
            authResponse.setRoles(Arrays.asList("USER"));
            return ResponseEntity.ok(authResponse);
        }
    }

    private boolean validateUser(MyUser myUser) {
        Optional<MyUser> optionalUser = myUserRepo.findByEmail(myUser.getEmail());
        System.out.println(optionalUser.toString());
        if (optionalUser.isPresent()) {
            return true;
        }
        return false;
    }
}
