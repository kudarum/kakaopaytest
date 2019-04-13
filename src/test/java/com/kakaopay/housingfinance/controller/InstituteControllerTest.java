package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.cmm.BaseTest;
import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.model.Institute;
import com.kakaopay.housingfinance.repository.InstituteRepository;
import com.kakaopay.housingfinance.service.InstituteService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class InstituteControllerTest extends BaseTest {

    @Autowired
    InstituteService instituteService;

    @Autowired
    InstituteRepository instituteRepository;

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
                    .contentType(MediaType.APPLICATION_JSON_UTF8) // 요청에 JSON 담아 보낼께
                    .accept(MediaType.APPLICATION_JSON_UTF8)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(200))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("result").exists())
                .andExpect(jsonPath("result[0].name").exists())
                .andExpect(jsonPath("result[0].code").exists());
    }

}