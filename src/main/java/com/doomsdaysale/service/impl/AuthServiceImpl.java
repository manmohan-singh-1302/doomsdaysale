package com.doomsdaysale.service.impl;

import com.doomsdaysale.config.JwtProvider;
import com.doomsdaysale.domain.USER_ROLE;
import com.doomsdaysale.model.Cart;
import com.doomsdaysale.model.User;
import com.doomsdaysale.model.VerificationCode;
import com.doomsdaysale.repository.CartRepository;
import com.doomsdaysale.repository.UserRepository;
import com.doomsdaysale.repository.VerificationCodeRepository;
import com.doomsdaysale.request.LoginRequest;
import com.doomsdaysale.response.AuthResponse;
import com.doomsdaysale.response.SignupRequest;
import com.doomsdaysale.service.AuthService;
import com.doomsdaysale.service.EmailService;
import com.doomsdaysale.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserService;
    @Override
    public void sentLoginOtp(String email) throws Exception {
        String name = "";
        String SIGNING_PREFIX = "signin_";
        if(email.startsWith(SIGNING_PREFIX)){
            email = email.substring(SIGNING_PREFIX.length());
            User user = userRepository.findByEmail(email);
            if(user == null){
                throw new Exception("User not found with email - "+email);
            }
           name = user.getFullName();
        }
        VerificationCode isExists = verificationCodeRepository.findByEmail(email);
        if(isExists != null){
            verificationCodeRepository.delete(isExists);
        }
        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);

        verificationCodeRepository.save(verificationCode);

        String subject = "Your OTP for DoomsdaySale";
        String text = "Dear customer, \n"+
                "\n" +
                " Thank you for choosing DoomsdaySale! To help ensure your security, we’ve sent you a One-Time Password (OTP) for verification.\n" +
                "\n" +
                "Your OTP: " +
                otp+
                " \nPlease enter this OTP on our website to complete your request. Remember, this code will expire in 10 minutes for your security.\n" +
                "\n" +
                "If you did not request this OTP or believe this was an error, please ignore this email or contact our support team immediately at [Support Email/Phone Number].\n" +
                "\n" +
                "Stay safe and happy shopping!\n" +
                "\n" +
                "Best regards,\n" +
                "The DoomsdaySale Team";
        System.out.println(name);
        emailService.sendVerificationOtpEmail(email, otp, subject, text);
    }

    @Override
    public String createUser(SignupRequest req) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

        // if the otp provided by the user is null or it does not match with the otp sent then we should throw an exception.
        if(verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("Wrong otp....");
        }

        User user = userRepository.findByEmail(req.getEmail());

        // if user does not exist create a new user.
        if(user == null){
            User createdUser = new User();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("8745693210");
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createdUser);
            // we need to create a cart everytime a new user is created.
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        // add the authority or role to the newly created user.
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signin(LoginRequest req) {
        String username = req.getEmail();
        String otp = req.getOtp();
        Authentication authentication = authenticate(username, otp);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("User successfully logged in");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) {
       UserDetails userDetails = customUserService.loadUserByUsername(username);

       if(userDetails == null){
           throw new BadCredentialsException("Invalid Username or Password");
       }
       VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);
       if(verificationCode == null || !verificationCode.getOtp().equals(otp)){
           throw new BadCredentialsException("Invalid Otp...");
       }
       return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }
}
