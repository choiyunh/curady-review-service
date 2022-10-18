package com.curady.reviewservice.domain.review.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RequestReview {
    private Long lectureId;
    private String content;
    private List<Long> keywordIds;
}
