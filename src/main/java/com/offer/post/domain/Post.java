package com.offer.post.domain;

import com.offer.member.Member;
import com.offer.post.application.request.PostUpdateRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.stream.Collectors;
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

    @Enumerated(value = EnumType.STRING)
    private TradeType tradeType;

    @Enumerated(value = EnumType.STRING)
    private ProductCondition productCondition;

    @Enumerated(value = EnumType.STRING)
    private TradeStatus tradeStatus;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public Post(Member seller, String title, int price, String category, String description,
        String thumbnailImageUrl, String location, TradeType tradeType, ProductCondition productCondition,
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

    public void addImages(List<PostImage> images) {
        for (PostImage image : images) {
            images.add(image);
            image.setPost(this);
        }
    }

    public void addImage(PostImage postImage) {
        images.add(postImage);
        postImage.setPost(this);
    }

    public List<String> getImageUrls() {
        return images.stream()
            .map(PostImage::getUrl)
            .collect(Collectors.toList());
    }

    public void deleteImages() {
        this.thumbnailImageUrl = null;
        this.images.clear();
    }

    public void update(PostUpdateRequest request) {
        this.title = request.getTitle();
        this.price = request.getPrice();
        this.category = request.getCategory();
        this.description = request.getDescription();
        this.thumbnailImageUrl = request.getThumbnailImageUrl();
        this.location = request.getLocation();
        this.tradeType = TradeType.from(request.getTradeType());
        this.productCondition = ProductCondition.from(request.getProductCondition());
        this.tradeStatus = TradeStatus.from(request.getTradeStatus());

        this.deleteImages();
        this.thumbnailImageUrl = request.getThumbnailImageUrl();
        List<String> imageUrls = request.getImageUrls();
        for (String imageUrl : imageUrls) {
            PostImage postImage = new PostImage(imageUrl);
            this.addImage(postImage);
        }
    }
}
