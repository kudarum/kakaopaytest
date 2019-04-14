package com.kakaopay.housingfinance.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "security.jwt.token")
@Getter
@Setter
public class JwtProperties {

    @NotEmpty
    private String jwtHeader;

    @NotEmpty
    private String secretKey;

    @NotEmpty
    private Long expireLength;
}
