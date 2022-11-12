package com.curady.reviewservice.domain.review.model;

import com.curady.reviewservice.domain.reviewKeyword.model.ReviewKeyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long lectureId;
    private boolean withdraw = false;

    @Column(length = 1000)
    private String content;
    @ColumnDefault("0")
    private Integer likes;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
    private List<ReviewKeyword> keywords = new ArrayList<>();

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Date createdAt;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Date updatedAt;

    public void withdraw() {
        this.withdraw = true;
    }

    public void increaseLikes() { this.likes++; }
    public void decreaseLikes() { this.likes--; }
}
