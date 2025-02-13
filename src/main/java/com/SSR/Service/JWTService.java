package com.SSR.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {
    @Value("${key}")
    private String algorithmKey;
    @Value("${issuer}")
    private String issuerName;
    @Value("${expiration}")
    private int expirationTime;

    private Algorithm algorithm;

    @PostConstruct
    public void PostConstruct() throws UnsupportedEncodingException {
     algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String createToken(String username) {
       return JWT.create()
                .withClaim("name",username)
                .withExpiresAt(new Date(System.currentTimeMillis()+expirationTime))
                .withIssuer(issuerName)
                .sign(algorithm);
    }
    public String getUsername(String username){
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer(issuerName)
                .build()
                .verify(username);
        return decodedJWT.getClaim("name").asString();

    }
}
