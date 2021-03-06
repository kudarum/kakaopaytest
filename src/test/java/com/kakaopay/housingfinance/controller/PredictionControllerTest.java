package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.cmm.BaseTest;
import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.model.Account;
import com.kakaopay.housingfinance.model.dto.PredictionDto;
import com.kakaopay.housingfinance.repository.AccountRepository;
import com.kakaopay.housingfinance.repository.PredictionRepository;
import com.kakaopay.housingfinance.util.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PredictionControllerTest extends BaseTest {

    @Autowired
    private PredictionRepository predictionRepository;

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
    @TestDescription("특정 은행의 특정 달에 대해서 검색 년도 해당 달에 금융지원 금액을 예측")
    public void test_GetPredictionFundMonth() throws Exception{

        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        PredictionDto predictionDto = PredictionDto.builder()
                .bank("국민은행")
                .month(2)
                .year(2018)
                .build();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/predictions/fund/month")
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(predictionDto))
        ) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("result.bank").exists())
                .andExpect(jsonPath("result.year").exists())
                .andExpect(jsonPath("result.month").exists())
                .andExpect(jsonPath("result.amount").exists());
    }

    @Test
    @TestDescription("연도 파라미터 없이 정상 조회시 정상 처리 - 특정 은행의 특정 달에 대해서 검색 년도 해당 달에 금융지원 금액을 예측")
    public void test_GetPredictionFundMonth_EmptyYearParam() throws Exception{

        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        PredictionDto predictionDto = PredictionDto.builder()
                .bank("국민은행")
                .month(2)
                .build();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/predictions/fund/month")
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(predictionDto))
        ) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("result.bank").exists())
                .andExpect(jsonPath("result.year").exists())
                .andExpect(jsonPath("result.month").exists())
                .andExpect(jsonPath("result.amount").exists());
    }

    @Test
    @TestDescription("6개월 이하 데이터 조회후 예측시 오류 발생 - 특정 은행의 특정 달에 대해서 검색 년도 해당 달에 금융지원 금액을 예측")
    public void test_GetPredictionFundMonth_LackData() throws Exception{

        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        PredictionDto predictionDto = PredictionDto.builder()
                .bank("국민은행")
                .year(2005)
                .month(6)
                .build();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/predictions/fund/month")
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(predictionDto))
        ) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(999)) // 커스텀 코드
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("result.bank").exists())
                .andExpect(jsonPath("result.year").exists())
                .andExpect(jsonPath("result.month").exists());
    }

    @Test
    @TestDescription("파라미터 없이 정상 조회 실패 - 특정 은행의 특정 달에 대해서 검색 년도 해당 달에 금융지원 금액을 예측")
    public void test_GetPredictionFundMonth_EmptyAllParam() throws Exception{

        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        PredictionDto predictionDto = PredictionDto.builder()
                .build();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/predictions/fund/month")
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(predictionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest()) // 오류가 나길 바란다.
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("message").exists());
    }

    @Test
    @TestDescription("토큰 없이 특정 은행의 특정 달에 대해서 검색 년도 해당 달에 금융지원 금액을 예측 조회 시 400 오류 발생")
    public void test_GetPredictionFundMonth_NoToken() throws Exception{

        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        PredictionDto predictionDto = PredictionDto.builder()
                .bank("국민은행")
                .month(2)
                .year(2018)
                .build();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/predictions/fund/month")
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(predictionDto))
        ) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("message").exists());
    }

}