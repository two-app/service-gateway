package com.two.servicegateway.filters;

import java.util.Set;

public class WhitelistedRoutes {
    public static Set<Route> WHITELISTED_ROUTES = Set.of(
            Route.POST("/self"),
            Route.POST("/login")
    );

    public static boolean contains(String path, String method) {
        return WHITELISTED_ROUTES.contains(Route.OF(path, method));
    }
}
