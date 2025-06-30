package com.doomsdaysale.repository;

import com.doomsdaysale.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Wishlist findByUserId(Long id);
}
