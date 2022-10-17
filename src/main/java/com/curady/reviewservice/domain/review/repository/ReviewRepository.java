package com.curady.reviewservice.domain.review.repository;

import com.curady.reviewservice.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
