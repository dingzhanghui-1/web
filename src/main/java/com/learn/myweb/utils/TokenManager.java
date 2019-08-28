package com.learn.myweb.utils;

import com.learn.myweb.redis.StringRedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TokenManager {

    private final long expireTime = 15 * 60 * 1000;

    private final String tokenSecret = "private_key";

    @Autowired
    private StringRedisService stringRedisService;

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(tokenSecret);

    private SecretKey signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    public String createToken(String userId) {
        long nowMillis = System.currentTimeMillis();
        Date nowDate = new Date(nowMillis);
        Date expiredDate = new Date(nowMillis + expireTime);
        JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString()).setHeaderParam("type", "jwt")
                .claim("userId", userId).setIssuedAt(nowDate).setIssuer("admin").setExpiration(expiredDate)
                .setNotBefore(nowDate).signWith(signatureAlgorithm, signingKey);
        String token = builder.compact();
        stringRedisService.putkeyValue(token, userId, 15, TimeUnit.MINUTES);
        return token;
    }

    public String parseUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        String userId = (String) claims.get("userId");
        return userId;
    }
}
