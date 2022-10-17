package com.curady.reviewservice.web.controller;

import com.curady.reviewservice.domain.review.service.ReviewService;
import com.curady.reviewservice.global.result.SingleResult;
import com.curady.reviewservice.global.service.ResponseService;
import com.curady.reviewservice.web.dto.RequestReview;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ResponseService responseService;
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록", description = "리뷰를 등록합니다.")
    @PostMapping("/auth/review")
    public SingleResult<Boolean> createReview(@RequestHeader("X-Authorization-Id") String userId,
                                              @RequestBody RequestReview requestReview) {
        return responseService.getSingleResult(reviewService.createReview(userId, requestReview));
    }
}
