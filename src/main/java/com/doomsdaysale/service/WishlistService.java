package com.doomsdaysale.service;

import com.doomsdaysale.model.Product;
import com.doomsdaysale.model.User;
import com.doomsdaysale.model.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface WishlistService {

    Wishlist createWishlist(User user);
    Wishlist getWishlistByUserId(User user);
    Wishlist addProductToWishlist(User user, Product product);
}
