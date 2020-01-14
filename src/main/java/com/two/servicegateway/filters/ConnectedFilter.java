package com.two.servicegateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class ConnectedFilter extends ZuulFilter {

    private final ConnectBlackList connectBlackList;
    private final JwtValidator jwtValidator;

    @Autowired
    public ConnectedFilter(ConnectBlackList connectBlackList, JwtValidator jwtValidator) {
        this.connectBlackList = connectBlackList;
        this.jwtValidator = jwtValidator;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        var ctx = RequestContext.getCurrentContext();
        var req = ctx.getRequest();

        boolean routeRequiresConnectToken = connectBlackList.contains(req.getRequestURI(), req.getMethod());
        boolean isConnectToken = jwtValidator.isConnectToken(req.getHeader("Authorization"));

        if ((routeRequiresConnectToken && !isConnectToken) || (!routeRequiresConnectToken && isConnectToken)) {
            ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            ctx.unset();
        }

        return null;
    }
}