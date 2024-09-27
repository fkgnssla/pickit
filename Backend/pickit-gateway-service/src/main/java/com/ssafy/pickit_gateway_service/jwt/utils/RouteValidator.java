package com.ssafy.pickit_gateway_service.jwt.utils;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class RouteValidator {
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    public static final List<String> openApiEndpoints = List.of(
        "/api/swagger-ui/**",
        "/api/v3/**",
        "/api/auth/login",
        "/api/auth/sign-up"
    );


    public Predicate<ServerHttpRequest> isSecured =
        request -> openApiEndpoints
            .stream()
            .noneMatch(uri -> pathMatcher.match(uri, request.getURI().getPath()));
}