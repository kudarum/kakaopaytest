package com.kakaopay.housingfinance.util;


import com.kakaopay.housingfinance.common.properties.JwtProperties;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.model.AccountRole;
import com.kakaopay.housingfinance.service.AccountService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    AccountService accountService;

    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = jwtProperties.getSecretKey();
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createJwt(String username, List<AccountRole> roles){

        if (StringUtils.isEmpty(username)) {
            return "";
        }

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles",roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getExpireLength());

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String refreshJwt(String tokenValue){
        try {
            String username = getUsername(tokenValue);

            List<AccountRole> roles = getRoles(tokenValue);

            return createJwt(username,roles);

        } catch (JwtException ignored) { }

        return "";
    }

    private List<AccountRole> getRoles(String tokenValue) {
        return (List<AccountRole>) Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(tokenValue)
                .getBody().get("roles");
    }

    public Authentication getAuthentication(String token) {
        UserDetailsService userDetailsService = (UserDetailsService) accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e ) {
            //return false;
            throw new JwtException(ApiResponseMessage.ERROR_NOT_RESOLVE_TOKEN.getMessage());
        }
    }
}