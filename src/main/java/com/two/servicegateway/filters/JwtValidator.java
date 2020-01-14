package com.two.servicegateway.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {
    private static final Logger logger = LoggerFactory.getLogger(JwtValidator.class);
    private final Algorithm algorithm = Algorithm.HMAC256("NOT_THE_REAL_SECRET");

    public boolean isTokenValid(String authHeader) {
        try {
            var token = getToken(authHeader);
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to extract token.", e);
            return false;
        } catch (JWTVerificationException e) {
            logger.warn("Failed to validate request.", e);
            return false;
        }
    }

    public boolean isConnectToken(String authHeader) {
        try {
            var token = getToken(authHeader);
            return !JWT.decode(token).getClaim("connectCode").isNull();
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to extract token.", e);
            return false;
        }
    }

    private String getToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer")) throw new IllegalArgumentException();

        var authParts = authHeader.split(" ");
        if (authParts.length != 2) throw new IllegalArgumentException();
        return authParts[1];
    }
}
