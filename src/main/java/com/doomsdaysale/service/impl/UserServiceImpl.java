// This class implements the methods of UserSerive interface.
package com.doomsdaysale.service.impl;

import com.doomsdaysale.model.User;
import com.doomsdaysale.repository.UserRepository;
import com.doomsdaysale.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User findUserByJwtToken(String jwt) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }
}
