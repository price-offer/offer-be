package com.offer.authentication.presentation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class LoginMember {

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

    public enum Authority {
        ANONYMOUS, MEMBER
    }
}
