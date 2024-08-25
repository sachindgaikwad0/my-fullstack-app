package com.codebook.service;

import com.codebook.model.AuthRequest;

public interface AuthService {

    public String  login(AuthRequest authRequest) throws Exception;

}
