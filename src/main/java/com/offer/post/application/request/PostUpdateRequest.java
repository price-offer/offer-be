package com.offer.post.application.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequest {

    private String title;
    private String category;
    private int price;
    private String location;
    private String productCondition;
    private String tradeStatus;
    private String tradeType;
    private String thumbnailImageUrl;
    private List<String> imageUrls;
    private String description;
}
