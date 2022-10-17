package com.curady.reviewservice.domain.reviewKeyword.model;

import com.curady.reviewservice.domain.keyword.model.Keyword;
import com.curady.reviewservice.domain.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;
}

