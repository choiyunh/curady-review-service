package com.curady.reviewservice.domain.review.dto;

import com.curady.reviewservice.domain.keyword.model.Keyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class ResponseReviewStatistics {
    private Long totalReview;
    private Long positiveKeywordRatio;
    private Long negativeKeywordRatio;
    private List<Map<String, Long>> positiveKeywordList;
    private List<Map<String, Long>> negativeKeywordList;
}
