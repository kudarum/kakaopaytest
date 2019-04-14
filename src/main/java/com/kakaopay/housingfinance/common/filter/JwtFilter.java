package com.kakaopay.housingfinance.common.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.housingfinance.common.response.ApiResponseBody;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;

        try {
            String token = jwtUtil.resolveToken((HttpServletRequest) req);

            if (token != null && jwtUtil.validateToken(token)) {
                Authentication auth = jwtUtil.getAuthentication(token);

                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (UsernameNotFoundException e) {
            // 유저를 찾지 못한경우.
            printErrorWriter(response,HttpStatus.BAD_REQUEST,e.getMessage());
            return;
        } catch (JwtException e) {
            // 토큰 변환 오류가 잘생한 경우
            printErrorWriter(response,HttpStatus.BAD_REQUEST,e.getMessage());
            return;
        } catch (Exception e) {
            printErrorWriter(response,HttpStatus.INTERNAL_SERVER_ERROR,ApiResponseMessage.ERROR_RUN_TIME_EXCEPTION.getMessage());
            return;
        }


        filterChain.doFilter(req, res);


    }

    private void printErrorWriter(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        ApiResponseBody apiResponseBody = new ApiResponseBody(status, message);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        String writeValueAsString = objectMapper.writeValueAsString(apiResponseBody);

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(status.value());
        response.getWriter().write(writeValueAsString);
    }


}