package com.curady.reviewservice.domain.likes.service;

import com.curady.reviewservice.domain.likes.model.Likes;
import com.curady.reviewservice.domain.likes.repository.LikesRepository;
import com.curady.reviewservice.domain.review.repository.ReviewRepository;
import com.curady.reviewservice.global.advice.exception.LikesAlreadyExistsException;
import com.curady.reviewservice.global.advice.exception.LikesNotFoundException;
import com.curady.reviewservice.global.advice.exception.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final ReviewRepository reviewRepository;

    public void createLikes(String userId, Long reviewId) {
        reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new).increaseLikes();
        Optional<Likes> likes = likesRepository.findByUserIdAndReviewId(Long.valueOf(userId), reviewId);
        if (likes.isPresent()) {
            throw new LikesAlreadyExistsException();
        }
        likesRepository.save(Likes.builder()
                .userId(Long.valueOf(userId))
                .reviewId(reviewId)
                .build());
    }

    public void deleteLikes(String userId, Long reviewId) {
        reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new).decreaseLikes();
        likesRepository.findByUserIdAndReviewId(Long.valueOf(userId), reviewId)
                .orElseThrow(LikesNotFoundException::new);
        likesRepository.deleteByUserIdAndReviewId(Long.valueOf(userId), reviewId);
    }

    @Transactional(readOnly = true)
    public Boolean isLiked(String userId, Long reviewId) {
        verifyLecture(reviewId);
        return likesRepository.findByUserIdAndReviewId(Long.valueOf(userId), reviewId).isPresent();
    }

    private void verifyLecture(Long reviewId) {
        reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
    }
}

