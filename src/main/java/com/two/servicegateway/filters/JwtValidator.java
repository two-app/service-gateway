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
        if (authHeader == null || !authHeader.startsWith("Bearer")) return false;

        var authParts = authHeader.split(" ");
        if (authParts.length != 2) return false;
        var token = authParts[1];

        try {
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            logger.warn("Failed to validate request.");
            return false;
        }
    }
}
