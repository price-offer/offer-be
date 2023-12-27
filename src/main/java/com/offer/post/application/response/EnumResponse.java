package com.offer.post.application.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnumResponse {

    private String code;
    private String name;

    public EnumResponse(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
