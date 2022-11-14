package com.curady.reviewservice.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseReviewsByUser {
    private Long reviewId;
    private Long lectureId;
    private int likes;
    private String content;
    private String lectureName;
    private String lectureVendorName;
    private List<String> keywordContent;
}
