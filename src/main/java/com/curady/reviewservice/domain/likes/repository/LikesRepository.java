package com.curady.reviewservice.domain.likes.repository;

import com.curady.reviewservice.domain.likes.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserIdAndReviewId(Long userId, Long reviewId);

    void deleteByUserIdAndReviewId(Long userId, Long reviewId);

    List<Likes> findAllByUserId(Long userId);
}
