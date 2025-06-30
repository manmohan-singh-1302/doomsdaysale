package com.doomsdaysale.service.impl;

import com.doomsdaysale.model.Cart;
import com.doomsdaysale.model.Coupon;
import com.doomsdaysale.model.User;
import com.doomsdaysale.repository.CartRepository;
import com.doomsdaysale.repository.CouponRepository;
import com.doomsdaysale.repository.UserRepository;
import com.doomsdaysale.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    @Override
    public Cart applyCoupon(String code, double orderValue, User user) throws Exception {

        Coupon coupon = couponRepository.findByCode(code);

        Cart cart = cartRepository.findByUserId(user.getId());

        if(coupon == null){
            throw new Exception("Invalid Coupon");
        }

        if(user.getUsedCoupons().contains(coupon)){
            throw new Exception("Coupon already used");
        }

        if(orderValue < coupon.getMinimumOrderValue()){
            throw new Exception("Valid for minimum order value "+ coupon.getMinimumOrderValue());
        }

        if(coupon.isActive() && LocalDate.now().isAfter(coupon.getValidityStartDate()) && LocalDate.now().isBefore(coupon.getValidityEndDate())){
            user.getUsedCoupons().add(coupon);
            userRepository.save(user);

            double dicountedPrice = (cart.getTotalSellingPrice()*coupon.getDiscountPercentage())/100;

            cart.setTotalSellingPrice(cart.getTotalSellingPrice()-dicountedPrice);
            cart.setCouponCode(code);
            cartRepository.save(cart);
            return cart;
        }
        throw new Exception("Invalid Coupon");
    }

    @Override
    public Cart removeCoupon(String code, User user) throws Exception {

        Coupon coupon = couponRepository.findByCode(code);

        if(coupon == null){
            throw new Exception("Coupoun not found...");
        }

        Cart cart = cartRepository.findByUserId(user.getId());

        double dicountedPrice = (cart.getTotalSellingPrice()*coupon.getDiscountPercentage())/100;

        cart.setTotalSellingPrice(cart.getTotalSellingPrice()+dicountedPrice);
        cart.setCouponCode(null);
        return cartRepository.save(cart);
    }

    @Override
    public Coupon findCouponById(Long id) throws Exception {
        return couponRepository.findById(id).orElseThrow(()->
                 new Exception("Coupon not found")
        );
    }

    @Override
    @PreAuthorize("hasRole ('Admin)")
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> findAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole ('Admin)")
    public void deleteCoupon(Long id) throws Exception {
        findCouponById(id);
        couponRepository.deleteById(id);
    }
}
