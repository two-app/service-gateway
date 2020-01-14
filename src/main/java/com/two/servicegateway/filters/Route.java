package com.two.servicegateway.filters;

import java.util.Objects;
import java.util.regex.Pattern;

public class Route {
    public final String path;
    public final String method;

    private Route(String path, String method) {
        this.path = path;
        this.method = method;
    }

    static Route OF(String path, String method) {
        return new Route(path.toLowerCase(), method.toUpperCase());
    }

    static Route GET(String path) {
        return new Route(path, "GET");
    }

    static Route POST(String path) {
        return new Route(path, "POST");
    }

    public boolean equalsRegex(String regex) {
        return Pattern.matches(regex, this.path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return path.equals(route.path) &&
                method.equals(route.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }
}
