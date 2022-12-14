package com.curady.reviewservice.domain.review.service;

import com.curady.reviewservice.domain.keyword.model.Keyword;
import com.curady.reviewservice.domain.keyword.repository.KeywordRepository;
import com.curady.reviewservice.domain.review.dto.ResponseReviewStatistics;
import com.curady.reviewservice.domain.review.dto.ResponseReviewsByLecture;
import com.curady.reviewservice.domain.review.dto.ResponseReviewsByUser;
import com.curady.reviewservice.domain.review.model.Review;
import com.curady.reviewservice.domain.review.repository.ReviewRepository;
import com.curady.reviewservice.domain.reviewKeyword.model.ReviewKeyword;
import com.curady.reviewservice.domain.reviewKeyword.repository.ReviewKeywordRepository;
import com.curady.reviewservice.domain.review.dto.RequestReview;
import com.curady.reviewservice.global.advice.exception.AccessReviewDeniedException;
import com.curady.reviewservice.global.advice.exception.ReviewAlreadyExistsException;
import com.curady.reviewservice.global.advice.exception.ReviewNotFoundException;
import com.curady.reviewservice.global.feign.LectureServiceFeignClient;
import com.curady.reviewservice.global.feign.UserServiceFeignClient;
import com.curady.reviewservice.global.feign.dto.ResponseLecture;
import com.curady.reviewservice.global.feign.dto.ResponseUserNicknameAndImage;
import com.curady.reviewservice.global.result.ReviewsResult;
import com.curady.reviewservice.global.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final UserServiceFeignClient userServiceFeignClient;
    private final LectureServiceFeignClient lectureServiceFeignClient;
    private final ResponseService responseService;

    @Transactional
    public void createReview(String userId, RequestReview requestReview) {
        if (reviewRepository.findByLectureIdAndUserIdAndWithdraw(
                requestReview.getLectureId(), Long.valueOf(userId), false).isPresent()) {
            throw new ReviewAlreadyExistsException();
        }

        Review review = reviewRepository.save(Review.builder()
                .userId(Long.valueOf(userId))
                .lectureId(requestReview.getLectureId())
                .content(requestReview.getContent())
                .likes(0)
                .build());

        List<Keyword> keywords = keywordRepository.findByIdIn(requestReview.getKeywordIds());
        List<ReviewKeyword> reviewKeywords = new ArrayList<>();
        keywords.forEach(v -> {
            reviewKeywords.add(ReviewKeyword.builder()
                    .review(review)
                    .withdraw(false)
                    .keyword(v)
                    .lectureId(requestReview.getLectureId())
                    .build());
        });
        reviewKeywordRepository.saveAll(reviewKeywords);
    }

    @Transactional
    public void deleteReview(String userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        if (!review.getUserId().equals(Long.valueOf(userId))) {
            throw new AccessReviewDeniedException();
        }
        List<ReviewKeyword> reviewKeywords = reviewKeywordRepository.findAllByReview(review);
        for (ReviewKeyword reviewKeyword : reviewKeywords) {
            reviewKeyword.withdraw();
        }
        review.withdraw();
    }

    @Transactional(readOnly = true)
    public ReviewsResult<ResponseReviewsByLecture> getReviewsByLecture(Long lectureId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByLectureIdAndWithdraw(lectureId, pageable, false);
        List<Long> userIds = new ArrayList<>();
        reviews.getContent().forEach(v -> {
            userIds.add(v.getUserId());
        });
        ResponseUserNicknameAndImage responseUserNicknameAndImages =
                userServiceFeignClient.getUserNicknameAndImg(userIds);

        List<ResponseReviewsByLecture> responseReviews = new ArrayList<>();
        for (int i = 0; i < reviews.getContent().size(); i++) {
            List<String> keywordContent = new ArrayList<>();
            List<ReviewKeyword> keywords = reviews.getContent().get(i).getKeywords();
            keywords.forEach(v -> {
                keywordContent.add(v.getKeyword().getContent());
            });

            responseReviews.add(
                    ResponseReviewsByLecture.builder()
                            .id(reviews.getContent().get(i).getId())
                            .nickname(responseUserNicknameAndImages.getData().get(i).getNickname())
                            .imageUrl(responseUserNicknameAndImages.getData().get(i).getImageUrl())
                            .content(reviews.getContent().get(i).getContent())
                            .keywordContent(keywordContent)
                            .build());
        }
        return responseService.getReviewsResult(reviews.getTotalPages(), reviews.getTotalElements(), responseReviews);
    }

    @Transactional(readOnly = true)
    public ReviewsResult<ResponseReviewsByUser> getReviewsByUser(String userId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByUserIdAndWithdraw(Long.valueOf(userId), pageable, false);
        List<Long> lectureIdList = new ArrayList<>();
        for (Review review : reviews.getContent()) {
            lectureIdList.add(review.getLectureId());
        }
        ResponseLecture responseLectures = lectureServiceFeignClient.getLectureNameAndVendor(lectureIdList);

        List<ResponseReviewsByUser> responseReviews = new ArrayList<>();
        for (int i = 0; i < reviews.getContent().size(); i++) {
            List<String> keywordContent = new ArrayList<>();
            List<ReviewKeyword> keywords = reviews.getContent().get(i).getKeywords();
            keywords.forEach(v -> {
                keywordContent.add(v.getKeyword().getContent());
            });

            responseReviews.add(
                    ResponseReviewsByUser.builder()
                            .reviewId(reviews.getContent().get(i).getId())
                            .lectureId(reviews.getContent().get(i).getLectureId())
                            .likes(reviews.getContent().get(i).getLikes())
                            .lectureName(responseLectures.getData().get(i).getName())
                            .lectureVendorName(responseLectures.getData().get(i).getVendorName())
                            .content(reviews.getContent().get(i).getContent())
                            .keywordContent(keywordContent)
                            .build());
        }
        return responseService.getReviewsResult(reviews.getTotalPages(), reviews.getTotalElements(), responseReviews);
    }

    @Transactional(readOnly = true)
    public ResponseReviewStatistics getReviewStatistics(Long lectureId) {
        Long totalReview = reviewRepository.countAllByLectureIdAndWithdraw(lectureId, false);
        List<ReviewKeyword> reviewKeywords = reviewKeywordRepository.findAllByLectureIdAndWithdraw(lectureId, false);
        int totalKeyword = reviewKeywords.size();
        if (totalKeyword == 0) {
            return ResponseReviewStatistics.builder()
                    .totalReview(totalReview)
                    .positiveKeywordRatio(0L)
                    .negativeKeywordRatio(0L)
                    .build();
        }

        int positiveCount = 0;
        int negativeCount = 0;
        List<Map<String, Long>> positiveKeywords = new ArrayList<>();
        List<Map<String, Long>> negativeKeywords = new ArrayList<>();
        Map<Keyword, Long> positiveKeywordMap = new LinkedHashMap<>();
        Map<Keyword, Long> negativeKeywordMap = new LinkedHashMap<>();
        for (ReviewKeyword reviewKeyword : reviewKeywords) {
            Keyword keyword = reviewKeyword.getKeyword();
            if (keyword.getType() == 0) {
                positiveCount++;
                if (positiveKeywordMap.containsKey(keyword)) {
                    positiveKeywordMap.put(keyword, positiveKeywordMap.get(keyword) + 1);
                } else {
                    positiveKeywordMap.put(keyword, 1L);
                }
            } else if (reviewKeyword.getKeyword().getType() == 1) {
                negativeCount++;
                if (negativeKeywordMap.containsKey(keyword)) {
                    negativeKeywordMap.put(keyword, negativeKeywordMap.get(keyword) + 1);
                } else {
                    negativeKeywordMap.put(keyword, 1L);
                }
            }
        }
        sortKeywords(positiveKeywords, positiveKeywordMap);
        sortKeywords(negativeKeywords, negativeKeywordMap);

        return ResponseReviewStatistics.builder()
                .totalReview(totalReview)
                .positiveKeywordCount(positiveCount)
                .negativeKeywordCount(negativeCount)
                .positiveKeywordRatio(Math.round((double) positiveCount / totalKeyword * 100))
                .negativeKeywordRatio(Math.round((double) negativeCount / totalKeyword * 100))
                .positiveKeywordList(positiveKeywords)
                .negativeKeywordList(negativeKeywords)
                .build();
    }

    private void sortKeywords(List<Map<String, Long>> keywords, Map<Keyword, Long> keywordMap) {
        List<Keyword> keySetList = new ArrayList<>(keywordMap.keySet());
        keySetList.sort(((o1, o2) -> (keywordMap.get(o2).compareTo(keywordMap.get(o1)))));

        for (Keyword key : keySetList) {
            Map<String, Long> temp = new LinkedHashMap<>();
            temp.put(key.getContent(), keywordMap.get(key));
            keywords.add(temp);
            if (keywords.size() == 3) {
                break;
            }
        }
    }

    public Boolean isRegistered(String userId, Long lectureId) {
        return reviewRepository.findByLectureIdAndUserIdAndWithdraw(lectureId, Long.valueOf(userId), false).isPresent();
    }
}
