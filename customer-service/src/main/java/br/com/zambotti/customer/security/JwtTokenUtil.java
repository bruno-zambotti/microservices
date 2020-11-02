package br.com.zambotti.customer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private int expire;

    public String generateToken(String username){
        Date createDate = getDateFromLocalDateTime(LocalDateTime.now());
        Date expirationDate = getDateFromLocalDateTime(LocalDateTime.now().plusMinutes(expire));
        return Jwts.builder()
                .addClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsername(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        return claims.getSubject();
    }

    private Date getDateFromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(OffsetDateTime.now().getOffset()));
    }

}
