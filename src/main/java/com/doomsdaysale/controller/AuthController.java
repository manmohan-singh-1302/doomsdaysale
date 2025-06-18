package com.doomsdaysale.controller;

import com.doomsdaysale.domain.USER_ROLE;
import com.doomsdaysale.model.User;
import com.doomsdaysale.model.VerificationCode;
import com.doomsdaysale.repository.UserRepository;
import com.doomsdaysale.repository.VerificationCodeRepository;
import com.doomsdaysale.request.LoginOtpRequest;
import com.doomsdaysale.request.LoginRequest;
import com.doomsdaysale.response.ApiResponse;
import com.doomsdaysale.response.AuthResponse;
import com.doomsdaysale.response.SignupRequest;
import com.doomsdaysale.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Rest-controllers are used to make a class web request handler. Rest controller is the combination of controller and requestbody annotation.
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // using this, we don't need to create a constructor for the repository to initialise.
public class AuthController {
    @Autowired
    private final UserRepository userRepository;
    private final AuthService authService;
    private final VerificationCodeRepository verificationCodeRepository;

    @PostMapping("/signup") // as we are posting data, we need to use PostMapping
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {
        // create a new user and set the email and full name
//        User user = new User();
//        user.setEmail(req.getEmail());
//        user.setFullName(req.getFullName());
//        User savedUser = userRepository.save(user);
        String jwt = authService.createUser(req);
        //AuthResponse is used to contain information about the authenticated user like user details, token, issue date, state etc. we only use authresponse in case of user authentication.
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("User Registration Successful");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);

        return ResponseEntity.ok(res);
    }
    @PostMapping("/sent/login-signup-otp") // as we are posting data, we need to use PostMapping
    public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {

        // remove this if an otp problem persists.
        VerificationCode isExists = verificationCodeRepository.findByEmail(req.getEmail());
        if(isExists != null){
            verificationCodeRepository.delete(isExists);
        }
        authService.sentLoginOtp(req.getEmail(), req.getRole());
        ApiResponse res = new ApiResponse();
        res.setMessage("Otp sent Successfully");

        return ResponseEntity.ok(res);
    }

    @PostMapping("/signin") // as we are posting data, we need to use PostMapping
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest    req) throws Exception {
        AuthResponse authResponse = authService.signing(req);

        return ResponseEntity.ok(authResponse);
    }
}
