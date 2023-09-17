package by.ITAcademy.UserMicroService.services.utils;


import by.ITAcademy.UserMicroService.config.property.JWTProperty;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Component
@Log4j2
public class JwtTokenHandler {

    private final JWTProperty jwtProperty;

    public JwtTokenHandler(JWTProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    public String generateAccessToken(UserDetails user) {
        return generateAccessToken(user.getUsername());
    }

    public String generateAccessToken(String name) {

        return Jwts.builder()
                .setSubject(name)
                .setIssuer(jwtProperty.getIssuer())
                .setIssuedAt(new Date())
       //         .claim("Secret", "EXAMPLE")
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtProperty.getSecret())
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();

        //return claims.get("SECRET", String.class);

        return claims.getSubject();
    }



    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperty.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) { // ошибка подписи
            log.error("Invalid JWT signature - {}", ex.getMessage());

        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) { // время действия токена вышло
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;

    }

}
