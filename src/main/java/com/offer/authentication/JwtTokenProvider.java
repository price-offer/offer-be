package com.offer.authentication;

public interface JwtTokenProvider {
    String createToken(final String subject);
    String extractSubject(final String token) throws IllegalArgumentException;
}
