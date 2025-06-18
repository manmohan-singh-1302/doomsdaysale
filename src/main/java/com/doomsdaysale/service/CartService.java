package com.doomsdaysale.service;

import com.doomsdaysale.model.Cart;
import com.doomsdaysale.model.CartItem;
import com.doomsdaysale.model.Product;
import com.doomsdaysale.model.User;

public interface CartService {

    CartItem addCartItem(User user, Product product, String size, int quantity);

    Cart findUserCart(User user);
}
