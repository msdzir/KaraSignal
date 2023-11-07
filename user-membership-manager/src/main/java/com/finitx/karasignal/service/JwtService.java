package com.finitx.karasignal.service;

import com.finitx.karasignal.util.DateTimeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class is responsible for generating and validating JSON web tokens.
 *
 * @author Amin Norouzi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final DateTimeUtil dateTimeUtil;

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expiration.access}")
    private Integer accessExpiration; // a day

    @Value("${jwt.token.expiration.refresh}")
    private Integer refreshExpiration; // 7 days

    /**
     * Builds a token from the given input.
     *
     * @param claims
     * @param userDetails
     * @param expiration
     * @return String
     */
    private String build(Map<String, Object> claims, UserDetails userDetails, Integer expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(dateTimeUtil.getNow())
                .setExpiration(dateTimeUtil.expiresAt(expiration, Calendar.MILLISECOND))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates refresh tokens based on the provided information.
     *
     * @param userDetails
     * @return String
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return build(new HashMap<>(), userDetails, refreshExpiration);
    }

    /**
     * Generates access tokens based on the provided information.
     *
     * @param userDetails
     * @return String
     */
    public String generateAccessToken(UserDetails userDetails) {
        return build(new HashMap<>(), userDetails, accessExpiration);
    }

    /**
     * Validates whether the given token and userDetails are valid.
     *
     * @param token
     * @param userDetails
     * @return boolean
     */
    public boolean validate(String token, UserDetails userDetails) {
        final String username = extract(token);
        return (username.equals(userDetails.getUsername())) && !isExpired(token);
    }

    /**
     * Extracts the subject or username from the given token.
     *
     * @param token
     * @return String
     */
    public String extract(String token) {
        return extract(token, Claims::getSubject);
    }

    /**
     * Extracts a generic object from the given token.
     *
     * @param token
     * @return <T> T
     */
    private <T> T extract(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAll(token);
        return resolver.apply(claims);
    }

    /**
     * Extracts all claims based on the given token.
     *
     * @param token
     * @return Claims
     */
    private Claims extractAll(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks whether the given token is expired or not.
     *
     * @param token
     * @return boolean
     */
    private boolean isExpired(String token) {
        Date date = extract(token, Claims::getExpiration);
        return dateTimeUtil.isExpired(date);
    }

    /**
     * Returns the signing key.
     *
     * @return Key
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
