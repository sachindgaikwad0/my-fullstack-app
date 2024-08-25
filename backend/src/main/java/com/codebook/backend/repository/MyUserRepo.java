package com.codebook.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codebook.backend.model.MyUser;

@Repository
public interface MyUserRepo extends JpaRepository<MyUser, Integer> {

    Optional<MyUser> findByEmail(String email);

}
