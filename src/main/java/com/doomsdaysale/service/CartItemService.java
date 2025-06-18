package com.doomsdaysale.service;

import com.doomsdaysale.model.CartItem;

public interface CartItemService {

    CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws Exception;
    void removeCartItem(Long userId, Long cartItemId) throws Exception;
    CartItem findCartItemById(Long id) throws Exception;
}
