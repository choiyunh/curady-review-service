package com.curady.reviewservice.web.controller;

import com.curady.reviewservice.domain.keyword.dto.ResponseKeywords;
import com.curady.reviewservice.domain.keyword.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping("/keywords")
    @Operation(summary = "키워드 목록 조회", description = "키워드 목록 조회/type 0은 긍정, 1은 부정")
    public List<ResponseKeywords> getKeywords() {
        return keywordService.getKeywords();
    }
}
