package up.pdp.appmeganews.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${app.jwt.expireDays}")
    private Integer expireDays;
    @Value("${app.jwt.secretKey}")
    private String secretKeyString;

    @Cacheable(value = "jwtUsername", key = "#username")
    public String generateToken(String username) {
        Date expireDate = new Date(System.currentTimeMillis() + expireDays * 24 * 60 * 60 * 1000);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(getKey())
                .compact();
    }

    @Cacheable(value = "jwtToken", key = "#token")
    public String getSubject(String token) {
        return ((Claims) Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parse(token)
                .getPayload()
        ).getSubject();
    }

    private SecretKey getKey() {
        byte[] decode = Base64.getDecoder().decode(secretKeyString);
        return Keys.hmacShaKeyFor(decode);
    }

}
