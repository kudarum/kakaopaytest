package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.cmm.BaseTest;
import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.model.Account;
import com.kakaopay.housingfinance.model.dto.FundStatsSearchDto;
import com.kakaopay.housingfinance.repository.AccountRepository;
import com.kakaopay.housingfinance.repository.FundRepository;
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

public class FundControllerTest extends BaseTest {

    @Autowired
    private FundRepository fundRepository;

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
    @TestDescription("년도별 각 금융기관의 지원금액 합계 조회 테스트")
    public void test_GetFundStatsYearSum() throws Exception{

        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearsum")
                    .header("Authorization",token)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("result[0].name").exists())
                .andExpect(jsonPath("result[1][0].year").exists())
                .andExpect(jsonPath("result[1][0].total_amount").exists())
                .andExpect(jsonPath("result[1][0].detail_amount").exists());

    }

    @Test
    @TestDescription("DB에 데이터가 없을 경우 정상 조회 -  년도별 각 금융기관의 지원금액 합계 조회")
    public void test_GetFundStatsYearSum_EmptyData() throws Exception{

        // 입력된 데이터가 없을 떄.

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearsum")
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("result[0].name").exists());
    }

    @Test
    @TestDescription("토큰 없이 년도별 각 금융기관의 지원금액 합계 조회시 403 오류 발생하는가.")
    public void test_GetFundStatsYearSum_NoToken() throws Exception{

        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearsum")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("message").exists());

    }

    @Test
    @TestDescription("각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관 조회")
    public void test_GetFundStatsYearSumMax() throws Exception{

        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearsum/max")
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("result.year").exists())
                .andExpect(jsonPath("result.bank").exists());
    }

    @Test
    @TestDescription("데이터 미 입력 시 정상 조회 - 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관 조회")
    public void test_GetFundStatsYearSumMax_EmptyData() throws Exception{

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearsum/max")
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
    @TestDescription("토큰 없이 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관 조회시 403오류 발생하는가.")
    public void test_GetFundStatsYearSumMax_NoToken() throws Exception{

        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearsum/max")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("message").exists());
    }

    @Test
    @TestDescription("전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회")
    public void test_GetFundStatsYearAvgMinMax() throws Exception{
        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        String institute_code = "bank008";

        FundStatsSearchDto fundStatsSearchDto = FundStatsSearchDto.builder()
                .institute_code(institute_code)
                .build();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearavg/min-max")
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .content(objectMapper.writeValueAsString(fundStatsSearchDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("result.bank").exists())
                .andExpect(jsonPath("result.support_amount").exists())
                .andExpect(jsonPath("result.support_amount[0].year").exists())
                .andExpect(jsonPath("result.support_amount[0].amount").exists())
                .andExpect(jsonPath("result.support_amount[1].year").exists())
                .andExpect(jsonPath("result.support_amount[1].amount").exists());
    }

    @Test
    @TestDescription("필수 파라미터 누락시 오류 발생하는지. -  전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회")
    public void test_GetFundStatsYearAvgMinMax_EmptyParam() throws Exception{

        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearavg/min-max")
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("message").exists());
    }

    @Test
    @TestDescription("데이터 미 입력 시 정상 조회 -  전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회")
    public void test_GetFundSRtatsYearAvgMinMax_EmptyData() throws Exception{

        String institute_code = "bank008";

        FundStatsSearchDto fundStatsSearchDto = FundStatsSearchDto.builder()
                .institute_code(institute_code)
                .build();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearavg/min-max")
                .header("Authorization",token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .content(objectMapper.writeValueAsString(fundStatsSearchDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists());
    }

    @Test
    @TestDescription("토큰 없이 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회시 403오류 발생하는가.")
    public void test_GetFundStatsYearAvgMinMax_NoToken() throws Exception{
        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        String institute_code = "bank008";

        FundStatsSearchDto fundStatsSearchDto = FundStatsSearchDto.builder()
                .institute_code(institute_code)
                .build();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearavg/min-max")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .content(objectMapper.writeValueAsString(fundStatsSearchDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("message").exists());
    }





}