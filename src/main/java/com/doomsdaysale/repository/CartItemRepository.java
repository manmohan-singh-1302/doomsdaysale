package com.doomsdaysale.repository;

import com.doomsdaysale.model.Cart;
import com.doomsdaysale.model.CartItem;
import com.doomsdaysale.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
