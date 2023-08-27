package com.offer.authentication.presentation;

import com.offer.authentication.JwtTokenProvider;
import com.offer.utils.AuthorizationTokenExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;

    public AuthenticationInterceptor(JwtTokenProvider jwtTokenProvider,
        final AuthenticationContext authenticationContext) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        String token = AuthorizationTokenExtractor.extractToken(request);
        if (Objects.nonNull(token)) {
            String subject = jwtTokenProvider.extractSubject(token);
            authenticationContext.setPrincipal(subject);
        }
        return true;
    }
}
