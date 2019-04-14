package com.kakaopay.housingfinance.service;

import com.kakaopay.housingfinance.cmm.BaseTest;
import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.model.Account;
import com.kakaopay.housingfinance.repository.AccountRepository;
import com.kakaopay.housingfinance.util.JwtUtil;
import io.jsonwebtoken.UnsupportedJwtException;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AccountServiceTest extends BaseTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    private String token;

    @Before
    public void setUp(){

        //token 발급.
        Account admin = accountRepository.findByUsername("admin");
        token = "Bearer " +jwtUtil.createJwt(admin.getUsername(),admin.getRoles());
    }

    @Test
    @TestDescription("회원가입")
    public void saveAccount() {
        Account account = Account.builder()
                .username("test088")
                .password("test088")
                .build();

        Account resultAcount = accountService.saveAccount(account);
        assertThat(resultAcount).isNotNull();
    }

    @Test
    @TestDescription("중복 회원가입")
    public void saveAccount_DuplicateAccount() {
        Account account = Account.builder()
                .username("admin")
                .password("admin")
                .build();

        Account resultAcount = accountService.saveAccount(account);
        assertThat(resultAcount).isNull();
    }


    @Test
    @TestDescription("로그인 후 토큰 발급 요청")
    public void getAccountPasswordMatch() {
        Account account = Account.builder()
                .username("admin")
                .password("admin")
                .build();

        Account resultAcount = accountService.getAccountPasswordMatch(account);
        assertThat(resultAcount).isNotNull();
    }

    @Test
    @TestDescription("틀린 비밀번호로 토큰 발급 요청시 null 리턴")
    public void getAccountPasswordMatch_NotPasswordMatch() {
        Account account = Account.builder()
                .username("admin")
                .password("admin1234")
                .build();

        Account resultAcount = accountService.getAccountPasswordMatch(account);
        assertThat(resultAcount).isNull();
    }

    @Test
    @TestDescription("토큰 재발급 요청")
    public void getRefreshToken() {

        String refreshToken = accountService.getRefreshToken(token);
        assertThat(refreshToken).isNotNull();

    }

    @Test
    @TestDescription("올바른 형태가 아닌 토큰으로 토큰 재발급 요청")
    public void getRefreshToken_NoToken() {

        assertThatThrownBy(() -> accountService.getRefreshToken("2122212"))
                .isInstanceOf(UnsupportedJwtException.class);

    }
}