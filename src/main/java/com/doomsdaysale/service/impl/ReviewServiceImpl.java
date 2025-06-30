package com.doomsdaysale.service.impl;

import com.doomsdaysale.model.Product;
import com.doomsdaysale.model.Review;
import com.doomsdaysale.model.User;
import com.doomsdaysale.repository.ReviewRepository;
import com.doomsdaysale.request.CreateReviewRequest;
import com.doomsdaysale.service.ReviewService;
import com.doomsdaysale.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    @Override
    public Review createReview(CreateReviewRequest req, User user, Product product) {
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReviewText(req.getReviewText());
        review.setRating(req.getReviewRating());
        review.setProductImage(req.getProductImages());

        product.getReviews().add(review);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception {

        Review review = getReviewById(reviewId);

        if(review.getUser().getId().equals(userId)){
            review.setReviewText(reviewText);
            review.setRating(rating);
            return reviewRepository.save(review);
        }
        throw new Exception("You cannot update this review");
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {

        Review review = getReviewById(reviewId);
        if(!review.getUser().getId().equals(userId)){
            throw new Exception("You cannot delete this review");
        }
        reviewRepository.delete(review);
    }

    @Override
    public Review getReviewById(Long reviewId) throws Exception {
        return reviewRepository.findById(reviewId).orElseThrow(()->
                new Exception("Review not found"));
    }
}
