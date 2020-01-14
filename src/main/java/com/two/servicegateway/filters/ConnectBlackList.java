package com.two.servicegateway.filters;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConnectBlackList {
    public static List<Route> ROUTES = List.of(
            Route.POST("/connect/.*")
    );

    public boolean contains(String path, String method) {
        Route route = Route.OF(path, method);
        return ROUTES.stream().anyMatch(r -> route.equalsRegex(r.path));
    }
}
