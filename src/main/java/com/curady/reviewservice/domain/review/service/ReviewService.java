package com.curady.reviewservice.domain.review.service;

import com.curady.reviewservice.domain.keyword.model.Keyword;
import com.curady.reviewservice.domain.keyword.repository.KeywordRepository;
import com.curady.reviewservice.domain.review.dto.ResponseReviews;
import com.curady.reviewservice.domain.review.model.Review;
import com.curady.reviewservice.domain.review.repository.ReviewRepository;
import com.curady.reviewservice.domain.reviewKeyword.model.ReviewKeyword;
import com.curady.reviewservice.domain.reviewKeyword.repository.ReviewKeywordRepository;
import com.curady.reviewservice.domain.review.dto.RequestReview;
import com.curady.reviewservice.global.feign.UserServiceFeignClient;
import com.curady.reviewservice.global.feign.dto.ResponseUserNicknameAndImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final UserServiceFeignClient userServiceFeignClient;

    @Transactional
    public void createReview(String userId, RequestReview requestReview) {
        Review review = reviewRepository.save(Review.builder()
                .userId(Long.valueOf(userId))
                .lectureId(requestReview.getLectureId())
                .content(requestReview.getContent())
                .build());

        List<Keyword> keywords = keywordRepository.findByIdIn(requestReview.getKeywordIds());
        keywords.forEach(v -> {
            reviewKeywordRepository.save(ReviewKeyword.builder()
                    .review(review)
                    .keyword(v)
                    .build());
        });
    }

    @Transactional
    public List<ResponseReviews> getReviews(Long lectureId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByLectureId(lectureId, pageable);
        List<Long> userIds = new ArrayList<>();
        reviews.getContent().forEach(v -> {
            userIds.add(v.getUserId());
        });
        ResponseUserNicknameAndImage responseUserNicknameAndImages =
                userServiceFeignClient.getUserNicknameAndImg(userIds);

        List<ResponseReviews> responseReviews = new ArrayList<>();
        for (int i = 0; i < reviews.getContent().size(); i++) {
            List<String> keywordContent = new ArrayList<>();
            List<ReviewKeyword> keywords = reviews.getContent().get(i).getKeywords();
            keywords.forEach(v -> {
                keywordContent.add(v.getKeyword().getContent());
            });

            responseReviews.add(
                    ResponseReviews.builder()
                            .nickname(responseUserNicknameAndImages.getData().get(i).getNickname())
                            .imageUrl(responseUserNicknameAndImages.getData().get(i).getImageUrl())
                            .content(reviews.getContent().get(i).getContent())
                            .keywordContent(keywordContent)
                            .build());
        }
        return responseReviews;
    }
}
