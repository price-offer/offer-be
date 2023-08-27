package com.offer.authentication.presentation;

import com.offer.authentication.presentation.LoginMember.Authority;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
@Setter
public class AuthenticationContext {

    private String principal;  // 토큰에서 추출한 memberId
    private Authority authority;
}
