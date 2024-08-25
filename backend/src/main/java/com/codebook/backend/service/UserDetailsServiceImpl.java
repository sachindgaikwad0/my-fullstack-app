package com.codebook.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codebook.backend.model.MyUser;
import com.codebook.backend.repository.MyUserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MyUserRepo myUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser;
        Optional<MyUser> optionalUser = myUserRepo.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        } else {
            myUser = optionalUser.get();
        }
        return User.builder().username(myUser.getEmail()).password(myUser.getPassword()).build();
    }

}
