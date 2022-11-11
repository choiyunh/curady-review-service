package com.curady.reviewservice.domain.likes.controller;

import com.curady.reviewservice.domain.likes.service.LikesService;
import com.curady.reviewservice.global.result.Result;
import com.curady.reviewservice.global.result.SingleResult;
import com.curady.reviewservice.global.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikesController {
    private final ResponseService responseService;
    private final LikesService likesService;

    @Operation(summary = "리뷰 좋아요 등록", description = "리뷰 좋아요 등록")
    @PostMapping("/auth/{reviewId}/likes")
    public Result createLikes(@RequestHeader("X-Authorization-Id") String userId,
                              @PathVariable Long reviewId) {
        likesService.createLikes(userId, reviewId);
        return responseService.getSuccessResult();
    }

    @Operation(summary = "리뷰 좋아요 삭제", description = "리뷰 좋아요 삭제")
    @DeleteMapping("/auth/{reviewId}/likes")
    public Result deleteLikes(@RequestHeader("X-Authorization-Id") String userId,
                              @PathVariable Long reviewId) {
        likesService.deleteLikes(userId, reviewId);
        return responseService.getSuccessResult();
    }

    @Operation(summary = "강의 좋아요 여부 반환", description = "강의 좋아요 여부 반환")
    @GetMapping("/auth/{reviewId}/likes")
    public SingleResult<Boolean> isLiked(@RequestHeader("X-Authorization-Id") String userId,
                                         @PathVariable Long reviewId) {
        return responseService.getSingleResult(likesService.isLiked(userId, reviewId));
    }
}
