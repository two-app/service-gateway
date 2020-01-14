package com.two.servicegateway.filters;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthWhiteList {
    public Set<Route> ROUTES = Set.of(
            Route.POST("/self"),
            Route.POST("/login")
    );

    public boolean contains(String path, String method) {
        return ROUTES.contains(Route.OF(path, method));
    }
}
