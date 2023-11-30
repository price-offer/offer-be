package com.offer.post.domain;

import com.offer.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member seller;

    private String title;
    private int price;
    private String category;
    private String description;
    private String thumbnailImageUrl;
    private String location;
    private String tradeType;
    private String productCondition;

    @Enumerated(value = EnumType.STRING)
    private TradeStatus tradeStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public Post(Member seller, String title, int price, String category, String description,
        String thumbnailImageUrl, String location, String tradeType, String productCondition,
        TradeStatus tradeStatus) {
        this.seller = seller;
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.location = location;
        this.tradeType = tradeType;
        this.productCondition = productCondition;
        this.tradeStatus = tradeStatus;
    }

    public boolean isWriter(Long memberId) {
        Objects.requireNonNull(memberId);
        return this.id.longValue() != memberId.longValue();
    }

    public void updateTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
}
