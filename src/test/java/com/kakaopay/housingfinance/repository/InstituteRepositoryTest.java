package com.kakaopay.housingfinance.repository;

import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.model.Institute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InstituteRepositoryTest {

    @Autowired
    InstituteRepository instituteRepository;

    @Test
    @TestDescription("applicationRunner 동작 시 기관이 재대로 생성되었는지 테스트")
    public void createdInstitute(){

        List<Institute> instituteList = instituteRepository.findAll();

        // 조회한 목록이 존재하는지 체크
        assertThat(instituteList).isEmpty();

    }
}