package com.offer.post.application.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SortPageReadParam {

    private String sort;
    private Long lastId;
    private int limit = 10;
}
