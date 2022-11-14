package com.curady.reviewservice.global.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewsResult <T> extends Result {
    private int totalPage;
    private long totalReview;
    private List<T> data;
}
