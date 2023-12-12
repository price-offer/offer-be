package com.offer.msg.domain;

import com.offer.member.Member;
import com.offer.offer.domain.Offer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {
        @Index(name = "msg_room_idx1", columnList = "member1_id, member2_id, offer_id"),
        @Index(name = "msg_room_idx2", columnList = "member2_id, member1_id, offer_id")
})
public class MsgRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member1_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member2_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member2;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Offer offer;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public MsgRoom(Member member1, Member member2, Offer offer) {
        this.member1 = member1;
        this.member2 = member2;
        this.offer = offer; // nullable
    }
}
