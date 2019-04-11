package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.cmm.BaseControllerTest;
import com.kakaopay.housingfinance.model.Institute;
import com.kakaopay.housingfinance.repository.InstituteRepository;
import com.kakaopay.housingfinance.service.InstituteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class InstituteControllerTest extends BaseControllerTest {

    @Autowired
    InstituteService instituteService;

    @Autowired
    InstituteRepository instituteRepository;

    // applicationRunner 동작 시 기관이 재대로 생성되었는지 테스트
    @Test
    public void createdInstitute() throws Exception{
        List<Institute> instituteList = instituteRepository.findAll();

        assertThat(instituteList).isNotEmpty();

    }

    // 기관 목록 조회 테스트
    @Test
    public void getInstituteList() throws Exception{
        mockMvc.perform(get("/institute/list")
                    .contentType(MediaType.APPLICATION_JSON_UTF8) // 요청에 JSON 담아 보낼께
                    .accept(MediaType.APPLICATION_JSON_UTF8)) // 나느 JSON 형태로 응답 받기를 원한다. 요구함.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(".code").exists())
                .andExpect(jsonPath(".name").exists());
    }









}