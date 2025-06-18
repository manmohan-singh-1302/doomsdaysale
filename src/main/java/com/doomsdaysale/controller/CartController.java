package com.doomsdaysale.controller;

import com.doomsdaysale.exceptions.ProductException;
import com.doomsdaysale.model.Cart;
import com.doomsdaysale.model.CartItem;
import com.doomsdaysale.model.Product;
import com.doomsdaysale.model.User;
import com.doomsdaysale.request.AddItemRequest;
import com.doomsdaysale.response.ApiResponse;
import com.doomsdaysale.service.CartItemService;
import com.doomsdaysale.service.CartService;
import com.doomsdaysale.service.ProductService;
import com.doomsdaysale.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;

    /*
    * Fetches the cart related to the user.
    * */
    @GetMapping
    public ResponseEntity<Cart> findUserCartHandler(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartService.findUserCart(user);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestHeader("Authorization") String jwt,
            @RequestBody AddItemRequest req
            ) throws ProductException, Exception{
        User user = userService.findUserByJwtToken(jwt);

        Product product = productService.findProductById(req.getProductId());

        CartItem item = cartService.addCartItem(user, product, req.getSize(), req.getQuantity());

        ApiResponse res = new ApiResponse();
        res.setMessage("Item added to cart successfully");

        return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
    }

    /*
    * Deletes a particular cartItem with the id.
    * */
    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId
    ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);

        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Item deleted from Cart successfully");

        return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId,
            @RequestBody CartItem cartItem
    ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);

        CartItem updatedCartItem = null;
        if(cartItem.getQuantity()>0){
            updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        }

        return new ResponseEntity<>(updatedCartItem, HttpStatus.ACCEPTED);
    }
}
