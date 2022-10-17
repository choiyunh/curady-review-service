package com.curady.reviewservice.global.advice;


import com.curady.reviewservice.global.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {
    private final ResponseService responseService;

}
