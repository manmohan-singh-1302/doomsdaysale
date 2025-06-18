// This interface provides the methods to find a user by using the Jwt token and by email.

package com.doomsdaysale.service;

import com.doomsdaysale.model.User;

public interface UserService {
    public  User findUserByJwtToken(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
}
