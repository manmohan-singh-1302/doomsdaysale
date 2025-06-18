package com.doomsdaysale.service.impl;

import com.doomsdaysale.model.CartItem;
import com.doomsdaysale.model.User;
import com.doomsdaysale.repository.CartItemRepository;
import com.doomsdaysale.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    @Override
    public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws Exception {
        CartItem item = findCartItemById(cartItemId);

        User cartItemUser = item.getCart().getUser();

        if(cartItemUser.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity()*item.getProduct().getSellingPrice());
            item.setSellingPrice(item.getQuantity()*item.getProduct().getSellingPrice());

            return cartItemRepository.save(item);
        } else throw new Exception("You can't update this cart item");
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
        CartItem item = findCartItemById(cartItemId);

        User cartItemUser = item.getCart().getUser();

        if(cartItemUser.getId().equals(userId)){
            cartItemRepository.delete(item);
        } else throw new Exception("You can't delete this item");
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws Exception {
        return cartItemRepository.findById(cartItemId).orElseThrow(()->
                new Exception("Cart Item not found with id - "+ cartItemId));
    }
}
