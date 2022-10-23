package com.curady.reviewservice.domain.keyword.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseKeywords {
    private Long id;
    private Long type;
    private String content;
}
