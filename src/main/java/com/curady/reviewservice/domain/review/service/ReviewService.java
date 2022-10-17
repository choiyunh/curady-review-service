package com.curady.reviewservice.domain.review.service;

import com.curady.reviewservice.domain.keyword.model.Keyword;
import com.curady.reviewservice.domain.keyword.repository.KeywordRepository;
import com.curady.reviewservice.domain.review.model.Review;
import com.curady.reviewservice.domain.review.repository.ReviewRepository;
import com.curady.reviewservice.domain.reviewKeyword.model.ReviewKeyword;
import com.curady.reviewservice.domain.reviewKeyword.repository.ReviewKeywordRepository;
import com.curady.reviewservice.web.dto.RequestReview;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final KeywordRepository keywordRepository;

    @Transactional
    public Boolean createReview(String userId, RequestReview requestReview) {
        Review review = reviewRepository.save(Review.builder()
                .userId(Long.valueOf(userId))
                .lectureId(requestReview.getLectureId())
                .content(requestReview.getContent())
                .build());

        List<Keyword> keywords = keywordRepository.findByIdIn(requestReview.getKeywordIds());
        for (Keyword keyword : keywords) {
            log.info(String.valueOf(keyword.getId()));
            log.info(keyword.getContent());
        }
        keywords.forEach(v -> {
            reviewKeywordRepository.save(ReviewKeyword.builder()
                    .review(review)
                    .keyword(v)
                    .build());
        });

        return true;
    }
}
