package com.project.carro.Utils.JWT;

import com.project.carro.Entity.UserEntity;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AccessTokenGenerator implements Serializable {
    @Autowired
    CustomUserDetails customUserDetails;
    @Value("${jwt.secret}")
    private String secret;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    public static final String ROLE = "ROLE_";
    public String generateAccessToken(UserEntity form) {
        User user = (User) customUserDetails.loadUserByUsername(form.getEmail());
        Map<String, Object> claims = new HashMap<>();
        return switch (form.getRole()) {
            case 1 ->
                    doGenerateToken(claims, user.getUsername(), user.getPassword(), UserEntity.Role.USER.getType());
            case 2 ->
                    doGenerateToken(claims, user.getUsername(), user.getPassword(), UserEntity.Role.SELLER.getType());
            case 3 ->
                    doGenerateToken(claims, user.getUsername(), user.getPassword(), UserEntity.Role.ADMIN.getType());
            default -> null;
        };
    }
    private String doGenerateToken(Map<String, Object> claims, String subject, String password, String type) {
        type=ROLE+type;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("carro")
                .claim("roles", type)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256,
                        secret)
                .compact();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for get any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey(secret);

        final Jws claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = (Claims) claimsJws.getBody();

        return claims;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //validate Token
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    //validate token
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public String getPurpose(String token){
        final Claims claims = getAllClaimsFromToken(token);
        String roles = (String) claims.get("roles");
        return roles;
    }

}
