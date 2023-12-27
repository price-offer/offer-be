package com.offer.offer.domain;

import com.offer.member.Member;
import com.offer.post.domain.Post;
import com.offer.post.domain.TradeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member offerer;

    private Integer price;
    private Boolean isSelected;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    @CreatedDate
    private LocalDateTime createdAt;

    public Offer(Post post, Member offerer, Integer price, Boolean isSelected, TradeType tradeType) {
        this.post = post;
        this.offerer = offerer;
        this.price = price;
        this.isSelected = isSelected;
        this.tradeType = tradeType;
    }

    public void selectOffer() {
        this.isSelected = true;
    }
}
