package com.offer.post.application.response;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImagesUploadResponse {

    private final List<String> imageUrls;

}
