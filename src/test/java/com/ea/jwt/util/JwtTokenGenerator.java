package com.ea.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.security.interfaces.RSAKey;
import java.util.HashMap;
import java.util.Map;

import static com.ea.jwt.util.PemUtils.getPrivateKeyFromPemFile;
import static com.ea.jwt.util.PemUtils.getPublicKeyFromPemFile;

public class JwtTokenGenerator {
    private static final String PRIVATE_KEY_FILE = "src/main/resources/certs/private.pem";
    private static final String PUBLIC_KEY_FILE = "src/main/resources/certs/public.pem";
    private static Key publicKey = getPublicKeyFromPemFile(PUBLIC_KEY_FILE);
    private static Key privateKey = getPrivateKeyFromPemFile(PRIVATE_KEY_FILE);
    private static Algorithm algorithmSign = Algorithm.RSA256((RSAKey) privateKey);
    private static Algorithm algorithmVerify = Algorithm.RSA256((RSAKey) publicKey);

    @Test
    public void test_token() {
        String token = generateJwtToken();
        System.out.println("JWT token: " + token);
        verify(token);
        verify2(token);
    }

    private String generateJwtToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", publicKey.toString());

        String token = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setClaims(claims)
//                    .setPayload("{\"sub\": " + publicKey +"}")
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
        return token;
    }

    public static DecodedJWT verify(String token) {
        JWTVerifier verifier = JWT.require(algorithmVerify)
                .build();

        return verifier.verify(token);
    }

    private String verify2(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        algorithmVerify.verify(decodedJWT);
        return token;
    }

}
