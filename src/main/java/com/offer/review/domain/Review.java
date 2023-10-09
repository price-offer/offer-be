package com.offer.review.domain;

import com.offer.member.Member;
import com.offer.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id", referencedColumnName = "id", nullable = false)
    private Member reviewee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id", nullable = false)
    private Member reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private int score;

    private String content;

    private boolean isRevieweeBuyer;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public Review(Member reviewee, Member reviewer, Post post, int score,
                  String content, boolean isRevieweeBuyer) {
        this.reviewee = reviewee;
        this.reviewer = reviewer;
        this.post = post;
        this.score = score;
        this.content = content;
        this.isRevieweeBuyer = isRevieweeBuyer;
    }
}