package com.doomsdaysale.service;

import com.doomsdaysale.model.Product;
import com.doomsdaysale.model.Review;
import com.doomsdaysale.model.User;
import com.doomsdaysale.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {

    Review createReview(CreateReviewRequest req, User user, Product product);
    List<Review> getReviewByProductId(Long productId);
    Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception;
    void deleteReview(Long reviewId, Long userId) throws Exception;
    Review getReviewById(Long reviewId) throws Exception;
}
