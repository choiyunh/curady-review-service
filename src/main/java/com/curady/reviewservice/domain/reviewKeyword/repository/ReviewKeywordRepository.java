package com.curady.reviewservice.domain.reviewKeyword.repository;

import com.curady.reviewservice.domain.reviewKeyword.model.ReviewKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long> {
}
