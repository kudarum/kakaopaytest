package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.cmm.BaseTest;
import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.model.Account;
import com.kakaopay.housingfinance.model.dto.AccountDto;
import com.kakaopay.housingfinance.repository.AccountRepository;
import com.kakaopay.housingfinance.service.AccountService;
import com.kakaopay.housingfinance.util.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AccountControllerTest extends BaseTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ModelMapper modelMapper;

    private String token;

    @Before
    public void setUp(){

        //token 발급.
        Account admin = accountRepository.findByUsername("admin");
        token = "Bearer " +jwtUtil.createJwt(admin.getUsername(),admin.getRoles());
    }


    @Test
    @TestDescription("회원가입 정상 테스트")
    public void test_SaveAccount() throws Exception {

        AccountDto accountDto = AccountDto.builder()
                .username("test067")
                .password("test067")
                .build();


        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(post("/accounts/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(accountDto))) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists());
    }

    @Test
    @TestDescription("회원가입 파라미터 누락 테스트")
    public void test_SaveAccount_NotParam() throws Exception {

        AccountDto accountDto = AccountDto.builder()
                .build();


        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(post("/accounts/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(accountDto))) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("message").exists());
    }

    @Test
    @TestDescription("중복 회원가입 시 오류 테스트")
    public void test_SaveAccount_Duplicate() throws Exception {

        AccountDto accountDto = AccountDto.builder()
                .username("test001")
                .password("test001")
                .build();

        //먼저 가입 실행/
        accountService.saveAccount(modelMapper.map(accountDto,Account.class));

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(post("/accounts/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(accountDto))) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("message").exists());
    }

    @Test
    @TestDescription("로그인 하면서 토큰 발급 요청")
    public void postAccountToken() throws Exception{

        AccountDto accountDto = AccountDto.builder()
                .username("test001")
                .password("test001")
                .build();

        accountService.saveAccount(modelMapper.map(accountDto,Account.class));

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(post("/accounts/token")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(accountDto))) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists());
    }

    @Test
    @TestDescription("비회원 로그인 하면서 토큰 발급 요청")
    public void postAccountToken_NoAcount() throws Exception{

        AccountDto accountDto = AccountDto.builder()
                .username("test099")
                .password("test099")
                .build();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(post("/accounts/token")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(accountDto))) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("message").exists());
    }

    @Test
    @TestDescription("정상적인 토큰 재발급 요청")
    public void getRefreshToken() throws  Exception {

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/accounts/token")
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists());
    }

    @Test
    @TestDescription("토큰 없이 재발급 요청 400에러 발생")
    public void getRefreshToken_NoToken() throws  Exception {

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/accounts/token")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("message").exists());
    }
}