package com.two.servicegateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class AuthorizationFilter extends ZuulFilter {

    private final AuthWhiteList authWhiteList;
    private final JwtValidator jwtValidator;

    @Autowired
    public AuthorizationFilter(AuthWhiteList authWhiteList, JwtValidator jwtValidator) {
        this.authWhiteList = authWhiteList;
        this.jwtValidator = jwtValidator;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * @return filters if the route is not whitelisted.
     */
    @Override
    public boolean shouldFilter() {
        var ctx = RequestContext.getCurrentContext();
        var req = ctx.getRequest();
        return !authWhiteList.contains(req.getRequestURI(), req.getMethod());
    }

    @Override
    public Object run() {
        var ctx = RequestContext.getCurrentContext();
        var req = ctx.getRequest();

        if (!jwtValidator.isTokenValid(req.getHeader("Authorization"))) {
            ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            ctx.unset();
        }

        return null;
    }
}
