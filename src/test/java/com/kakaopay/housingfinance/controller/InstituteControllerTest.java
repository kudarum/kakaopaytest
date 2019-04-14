package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.cmm.BaseTest;
import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.model.Account;
import com.kakaopay.housingfinance.model.Institute;
import com.kakaopay.housingfinance.repository.AccountRepository;
import com.kakaopay.housingfinance.repository.InstituteRepository;
import com.kakaopay.housingfinance.service.InstituteService;
import com.kakaopay.housingfinance.util.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class InstituteControllerTest extends BaseTest {

    @Autowired
    private InstituteService instituteService;

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private String token;

    @Before
    public void setUp(){

        //token 발급.
        Account admin = accountRepository.findByUsername("admin");
        token = "Bearer " +jwtUtil.createJwt(admin.getUsername(),admin.getRoles());
    }


    @Test
    @TestDescription("applicationRunner 동작 시 주택금융 기관이 재대로 생성되었는지 테스트")
    public void test_CreatedInstitute() throws Exception{
        List<Institute> instituteList = instituteRepository.findAll();

        assertThat(instituteList).isNotEmpty();
    }

    // 주택금융 기관 목록 조회 테스트
    @Test
    @TestDescription("주택금융 기관 목록 조회 테스트")
    public void test_GetInstituteList() throws Exception{

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/institutes")
                .header("Authorization",token)
                    .contentType(MediaType.APPLICATION_JSON_UTF8) // 요청에 JSON 담아 보낼께
                    .accept(MediaType.APPLICATION_JSON_UTF8)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("result[0].name").exists())
                .andExpect(jsonPath("result[0].code").exists());
    }

    @Test
    @TestDescription("jwt 토큰 없이 주택금융 기관 목록 조회시 403 오류 발생하는가.")
    public void test_GetInstituteList_NoToken() throws Exception{

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/institutes")
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("message").exists());
    }

}