package com.doomsdaysale.service;

import com.doomsdaysale.request.LoginRequest;
import com.doomsdaysale.response.AuthResponse;
import com.doomsdaysale.response.SignupRequest;

public interface AuthService {
    void sentLoginOtp(String email) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signin (LoginRequest req);
}
