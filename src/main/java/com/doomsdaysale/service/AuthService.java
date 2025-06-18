package com.doomsdaysale.service;

import com.doomsdaysale.domain.USER_ROLE;
import com.doomsdaysale.request.LoginRequest;
import com.doomsdaysale.response.AuthResponse;
import com.doomsdaysale.response.SignupRequest;

public interface AuthService {
    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing (LoginRequest req) throws Exception;
}
