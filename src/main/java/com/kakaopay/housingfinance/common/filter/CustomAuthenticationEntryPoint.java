package com.kakaopay.housingfinance.common.filter;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.housingfinance.common.response.ApiResponseBody;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        ApiResponseBody apiResponseBody = new ApiResponseBody(HttpStatus.FORBIDDEN, ApiResponseMessage.ERROR_UNAUTHORIZED.getMessage());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        String writeValueAsString = objectMapper.writeValueAsString(apiResponseBody);

        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        res.setStatus(403);
        res.getWriter().write(writeValueAsString);
    }
}