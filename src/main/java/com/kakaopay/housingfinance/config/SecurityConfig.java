package com.kakaopay.housingfinance.config;

import com.kakaopay.housingfinance.common.filter.entrypoint.CustomAuthenticationEntryPoint;
import com.kakaopay.housingfinance.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtUtil jwtUtil;


    private static final String ROOT_ENTRY_POINT = "/**";
    private static final String ACCOUNT_ENTRY_POINT = "/accounts/**";
    private static final String INSTITUTES_ENTRY_POINT = "/institutes/**";
    private static final String FUNDS_ENTRY_POINT = "/funds/**";
    private static final String PREDICITION_ENTRY_POINT = "/predictions/**";
    private static final String FILE_ENTRY_POINT = "/files/**";


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(ACCOUNT_ENTRY_POINT);
        web.ignoring().antMatchers("/resources/**");

    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(ROOT_ENTRY_POINT).authenticated()
                .antMatchers(ACCOUNT_ENTRY_POINT).permitAll()
                .antMatchers(INSTITUTES_ENTRY_POINT).permitAll()
                .antMatchers(FUNDS_ENTRY_POINT).permitAll()
                .antMatchers(PREDICITION_ENTRY_POINT).permitAll()
                .antMatchers(FILE_ENTRY_POINT).permitAll()
                .and().apply(new JwtConfig(jwtUtil))
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

    }



}
