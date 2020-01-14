package com.two.servicegateway.filters;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthWhiteList {
    private Set<Route> routes = Set.of(
            Route.POST("/self"),
            Route.POST("/login")
    );

    public boolean contains(String path, String method) {
        return routes.contains(Route.OF(path, method));
    }
}
