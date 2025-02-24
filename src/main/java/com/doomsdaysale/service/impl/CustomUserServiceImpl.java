package com.doomsdaysale.service.impl;

import com.doomsdaysale.domain.USER_ROLE;
import com.doomsdaysale.model.User;
import com.doomsdaysale.repository.SellerRepository;
import com.doomsdaysale.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.doomsdaysale.model.Seller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    // if the user email contains the prefix seller_ then we need to check in the seller table or else user table.
    private static final String SELLER_PREFIX = "seller_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.startsWith(SELLER_PREFIX)){
            String actualUserName = username.substring(SELLER_PREFIX.length());
            Seller seller = sellerRepository.findByEmail(actualUserName);
            if(seller!=null){
                return buildUserDetails(seller.getEmail(), seller.getPassword(), seller.getRole());
            }
        }else{
            User user = userRepository.findByEmail(username);
            if(user!=null){
                return buildUserDetails(user.getEmail(), user.getPassword(), user.getRole());
            }

        }
        throw new UsernameNotFoundException("User or seller not found with email - "+ username);
    }

    private UserDetails buildUserDetails(String email, String password, USER_ROLE role) {
        if(role == null){
            role = USER_ROLE.ROLE_CUSTOMER;
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return new org.springframework.security.core.userdetails.User(
                email,
                password,
                authorities
        );
    }
}
