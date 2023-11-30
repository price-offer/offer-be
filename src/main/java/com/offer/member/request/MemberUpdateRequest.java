package com.offer.member.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {

    private String nickname;
    private String profileImageUrl;
}
