package com.curady.reviewservice.domain.reviewKeyword.repository;

import com.curady.reviewservice.domain.review.model.Review;
import com.curady.reviewservice.domain.reviewKeyword.model.ReviewKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long> {
    List<ReviewKeyword> findAllByLectureId(Long lectureId);
    List<ReviewKeyword> findAllByReview(Review review);
}
