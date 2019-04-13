package com.kakaopay.housingfinance.service;


import com.kakaopay.housingfinance.cmm.BaseTest;
import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.model.Institute;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InstituteServiceTest extends BaseTest {

    @Autowired
    InstituteService instituteService;


    @Test
    @TestDescription("Serivce - 주택금융 기관 조회 테스트")
    public void getInstitutes(){
        List<Institute> instituteList = instituteService.getInstituteAllList();

        // 조회한 목록 데이터가 존재하는지 체크
        assertThat(instituteList).isNotEmpty();

        for(int institute_count = 0 ; institute_count < instituteList.size() ; institute_count++) {
            // 코드와 이름이 존재하냐
            assertThat(instituteList.get(institute_count).getCode()).isNotEmpty();
            assertThat(instituteList.get(institute_count).getName()).isNotEmpty();
        }

    }
}