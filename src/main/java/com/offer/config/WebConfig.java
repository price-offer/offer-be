package com.offer.config;

import com.offer.authentication.presentation.AuthenticationInterceptor;
import com.offer.authentication.presentation.AuthenticationPrincipalArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;
    private final AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor);
        registry.addInterceptor(new LoggingInterceptor());
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
    }
}
