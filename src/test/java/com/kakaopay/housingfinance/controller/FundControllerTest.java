package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.cmm.BaseTest;
import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.repository.FundRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FundControllerTest extends BaseTest {

    @Autowired
    FundRepository fundRepository;

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
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                    .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("status").value(ApiResponseMessage.RESPONSE_SUCCESS_CODE.getMessage()))
                .andExpect(jsonPath("message").value(ApiResponseMessage.RESPONSE_SUCCESS.getMessage()))
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
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("status").value(ApiResponseMessage.RESPONSE_SUCCESS_CODE.getMessage()))
                .andExpect(jsonPath("message").value(ApiResponseMessage.RESPONSE_SUCCESS.getMessage()))
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("result[0].name").exists());
    }

    @Test
    @TestDescription("전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회")
    public void test_GetFundStatsYearSumMax() throws Exception{

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
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("status").value(ApiResponseMessage.RESPONSE_SUCCESS_CODE.getMessage()))
                .andExpect(jsonPath("message").value(ApiResponseMessage.RESPONSE_SUCCESS.getMessage()))
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("result.year").exists())
                .andExpect(jsonPath("result.bank").exists());
    }

    @Test
    @TestDescription("데이터 미 입력 시 정상 조회 - 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회")
    public void test_GetFundStatsYearSumMax_EmptyData() throws Exception{

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearsum/max")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("status").value(ApiResponseMessage.RESPONSE_SUCCESS_CODE.getMessage()))
                .andExpect(jsonPath("message").value(ApiResponseMessage.RESPONSE_SUCCESS.getMessage()))
                .andExpect(jsonPath("result").exists());
    }

    @Test
    @TestDescription("전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회")
    public void test_GetFundStatsYearAvgMinMax() throws Exception{
        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearavg/min-max")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .param("institute_code","bank008"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("status").value(ApiResponseMessage.RESPONSE_SUCCESS_CODE.getMessage()))
                .andExpect(jsonPath("message").value(ApiResponseMessage.RESPONSE_SUCCESS.getMessage()))
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("result.bank").exists())
                .andExpect(jsonPath("result.support_amount").exists())
                .andExpect(jsonPath("result.support_amount[0].year").exists())
                .andExpect(jsonPath("result.support_amount[0].amount").exists())
                .andExpect(jsonPath("result.support_amount[1].year").exists())
                .andExpect(jsonPath("result.support_amount[1].amount").exists());
    }

    @Test
    @TestDescription("데이터 미 입력 시 정상 조회 -  전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회")
    public void test_GetFundStatsYearAvgMinMax_EmptyParam() throws Exception{

        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearavg/min-max")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("status").value(ApiResponseMessage.RESPONSE_FAIL_CODE.getMessage()))
                .andExpect(jsonPath("message").value(ApiResponseMessage.RESPONSE_FAIL.getMessage()))
                .andExpect(jsonPath("errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @TestDescription("데이터 미 입력 시 정상 조회 -  전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회")
    public void test_GetFundStatsYearAvgMinMax_EmptyData() throws Exception{

        // 리스트 조회후 정상적으로 데이터가 리턴 되는지 체크
        mockMvc.perform(get("/funds/stats/yearavg/min-max")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .param("institute_code","bank008"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("status").value(ApiResponseMessage.RESPONSE_SUCCESS_CODE.getMessage()))
                .andExpect(jsonPath("message").value(ApiResponseMessage.RESPONSE_SUCCESS.getMessage()))
                .andExpect(jsonPath("result").exists());
    }




}