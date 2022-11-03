package com.curady.reviewservice.global.advice;


import com.curady.reviewservice.global.advice.exception.ReviewAlreadyExistsException;
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
    public Result lectureNotFoundException() {
        return responseService.getFailureResult(-301, "이미 리뷰를 등록한 강의입니다.");
    }
}
