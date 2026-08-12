package com.radhika.iconicstore.service;

import com.radhika.iconicstore.dto.request.LoginRequest;
import com.radhika.iconicstore.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
