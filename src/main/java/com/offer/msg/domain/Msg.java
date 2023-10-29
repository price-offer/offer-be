package com.offer.msg.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Msg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private MsgRoom room;

    private String content; // TODO: 최대 길이 지정

    private Long senderId; // TODO: 연관관계 필요성 없음.

    @Column(length = 1)
    private boolean isRead = false;

    @CreatedDate
    private LocalDateTime createdAt;

    public void markRead() {
        this.isRead = true;
    }

    @Builder
    public Msg(MsgRoom room, String content, Long senderId) {
        this.room = room;
        this.content = content;
        this.senderId = senderId;
    }
}
