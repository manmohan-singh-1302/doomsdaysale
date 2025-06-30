package com.doomsdaysale.controller;

import com.doomsdaysale.model.Product;
import com.doomsdaysale.model.User;
import com.doomsdaysale.model.Wishlist;
import com.doomsdaysale.service.ProductService;
import com.doomsdaysale.service.UserService;
import com.doomsdaysale.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Wishlist> getWishlistByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Wishlist wishlist = wishlistService.getWishlistByUserId(user);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist> addProductToWishlist(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        Product product = productService.findProductById(productId);
        User user = userService.findUserByJwtToken(jwt);
        Wishlist updateWishlist = wishlistService.addProductToWishlist(user, product);

        return ResponseEntity.ok(updateWishlist);
    }
}
