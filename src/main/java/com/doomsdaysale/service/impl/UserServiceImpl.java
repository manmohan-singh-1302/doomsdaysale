// This class implements the methods of UserSerive interface.
package com.doomsdaysale.service.impl;

import com.doomsdaysale.config.JwtProvider;
import com.doomsdaysale.model.User;
import com.doomsdaysale.repository.UserRepository;
import com.doomsdaysale.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User not found with email - "+email);
        }
        return user;
    }
}
