package com.codebook.service;

import com.codebook.model.MyUser;
import com.codebook.repository.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private MyUserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        MyUser admin = new MyUser();
        admin.setEmail("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles("ADMIN,USER");
        userRepository.save(admin);

        MyUser user = new MyUser();
        user.setEmail("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles("USER");
        userRepository.save(user);
    }
}
