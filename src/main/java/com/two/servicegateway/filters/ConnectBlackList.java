package com.two.servicegateway.filters;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConnectBlackList {
    private List<Route> routes = List.of(
            Route.POST("/connect/.*")
    );

    public boolean contains(String path, String method) {
        Route route = Route.OF(path, method);
        return routes.stream().anyMatch(r -> route.equalsRegex(r.path));
    }
}
