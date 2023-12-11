package com.mds.system.littleshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    public String getToken(Authentication authentication) {
        String userName = authentication.getName();
        Date now = new Date();
        Date tokenExpiration = new Date(now.getTime() + SecurityConstants.JWT_EXPIRATION_TOKEN);

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(tokenExpiration)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SIGN)
                .compact();
    }

    public String getJwtUserName(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SIGN)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean tokenValidation(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.JWT_SIGN).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT has expired or It's invalid");
        }
    }
}
