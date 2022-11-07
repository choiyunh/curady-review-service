package com.curady.reviewservice.domain.review.controller;

import com.curady.reviewservice.domain.review.dto.ResponseReviewStatistics;
import com.curady.reviewservice.domain.review.dto.ResponseReviews;
import com.curady.reviewservice.domain.review.service.ReviewService;
import com.curady.reviewservice.global.result.MultipleResult;
import com.curady.reviewservice.global.result.Result;
import com.curady.reviewservice.global.result.SingleResult;
import com.curady.reviewservice.global.service.ResponseService;
import com.curady.reviewservice.domain.review.dto.RequestReview;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ResponseService responseService;
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록", description = "리뷰를 등록합니다.")
    @PostMapping("/auth/review")
    public Result createReview(@RequestHeader("X-Authorization-Id") String userId,
                               @RequestBody RequestReview requestReview) {
        reviewService.createReview(userId, requestReview);
        return responseService.getSuccessResult();
    }

    @Operation(summary = "강의별 리뷰 목록 조회(페이징 적용)", description = "강의별 리뷰 목록을 페이지별로 반환합니다. page의 기본값은 1, size는 6, sort는 id,DESC")
    @GetMapping("/reviews/{lectureId}")
    public MultipleResult<ResponseReviews> getReviews(@PathVariable Long lectureId,
                                                      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return responseService.getMultipleResult(reviewService.getReviews(lectureId, pageable));
    }

    @Operation(summary = "유저별 리뷰 목록 조회(페이징 적용)", description = "유저별 리뷰 목록을 페이지별로 반환합니다. page의 기본값은 1, size는 6, sort는 id,DESC")
    @GetMapping("/auth/reviews")
    public MultipleResult<ResponseReviews> getReviewsByUser(@RequestHeader("X-Authorization-Id") String userId,
                                                            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return responseService.getMultipleResult(reviewService.getReviewsByUser(userId, pageable));
    }

    @Operation(summary = "리뷰 개수 및 키워드 통계 조회", description = "리뷰 개수 및 키워드 통계 조회")
    @GetMapping("/review/statistics/{lectureId}")
    public SingleResult<ResponseReviewStatistics> getReviews(@PathVariable Long lectureId) {
        return responseService.getSingleResult(reviewService.getReviewStatistics(lectureId));
    }

    @Operation(summary = "리뷰 등록 여부 조회", description = "리뷰 등록 여부 조회")
    @GetMapping("/auth/review/{lectureId}")
    public SingleResult<Boolean> isRegistered(@RequestHeader("X-Authorization-Id") String userId,
                                              @PathVariable Long lectureId) {
        return responseService.getSingleResult(reviewService.isRegistered(userId, lectureId));
    }
}