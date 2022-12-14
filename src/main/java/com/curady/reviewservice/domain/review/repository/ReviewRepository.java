package com.curady.reviewservice.domain.review.repository;

import com.curady.reviewservice.domain.review.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByLectureIdAndWithdraw(Long lectureId, Pageable pageable, boolean withdraw);

    Page<Review> findAllByUserIdAndWithdraw(Long userId, Pageable pageable, boolean withdraw);

    Long countAllByLectureIdAndWithdraw(Long lectureId, boolean withdraw);

    Optional<Review> findByLectureIdAndUserIdAndWithdraw(Long lectureId, Long userId, boolean withdraw);
}
