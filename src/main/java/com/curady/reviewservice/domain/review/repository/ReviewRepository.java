package com.curady.reviewservice.domain.review.repository;

import com.curady.reviewservice.domain.review.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByLectureId(Long lectureId, Pageable pageable);

    Long countAllByLectureId(Long lectureId);

    Optional<Review> findByLectureIdAndUserId(Long lectureId, Long userId);
}
