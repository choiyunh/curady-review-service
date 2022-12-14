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
public class ResponseReviewsByLecture {
    private Long id;
    private String nickname;
    private String imageUrl;
    private String content;
    private List<String> keywordContent;
}
