package com.doomsdaysale.controller;

import com.doomsdaysale.domain.PaymentMethod;
import com.doomsdaysale.model.Address;
import com.doomsdaysale.model.Cart;
import com.doomsdaysale.model.User;
import com.doomsdaysale.response.PaymentLinkResponse;
import com.doomsdaysale.service.CartService;
import com.doomsdaysale.service.OrderService;
import com.doomsdaysale.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createOrderHandler(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Address shippingAddress,
            @RequestParam PaymentMethod paymentMethod
            ) throws Exception{

        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);


    }

}
