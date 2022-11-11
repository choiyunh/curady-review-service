package com.curady.reviewservice.global.advice;


import com.curady.reviewservice.global.advice.exception.*;
import com.curady.reviewservice.global.result.Result;
import com.curady.reviewservice.global.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {
    private final ResponseService responseService;

    @ExceptionHandler(ReviewAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result reviewAlreadyExistsException() {
        return responseService.getFailureResult(-301, "이미 리뷰를 등록한 강의입니다.");
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result reviewNotFoundException() {
        return responseService.getFailureResult(-302, "리뷰를 찾을 수 없습니다.");
    }

    @ExceptionHandler(AccessReviewDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result accessReviewDeniedException() {
        return responseService.getFailureResult(-303, "본인이 작성한 리뷰만 삭제할 수 있습니다.");
    }

    @ExceptionHandler(LikesAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result likesAlreadyExistsException() {
        return responseService.getFailureResult(-304, "이미 좋아요 한 리뷰입니다.");
    }

    @ExceptionHandler(LikesNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result likesNotFoundException() {
        return responseService.getFailureResult(-305, "좋아요하지 않은 리뷰입니다.");
    }

}
