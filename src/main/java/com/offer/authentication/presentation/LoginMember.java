package com.offer.authentication.presentation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginMember {

    public enum Authority {
        ANONYMOUS, MEMBER
    }

    private Long id;
    private Authority authority;

    public LoginMember(Authority authority) {
        this(null, authority);
    }

    public LoginMember(Long id, Authority authority) {
        this.id = id;
        this.authority = authority;
    }

    public boolean isAnonymous() {
        return Authority.ANONYMOUS.equals(authority);
    }

    public boolean isMember() {
        return Authority.MEMBER.equals(authority);
    }
}
