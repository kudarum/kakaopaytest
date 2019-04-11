package com.kakaopay.housingfinance.repository;

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

    // applicationRunner 동작 시 기관이 재대로 생성되었는지 테스트
    @Test
    public void createdInstitute(){
        List<Institute> instituteList = instituteRepository.findAll();

        assertThat(instituteList).isEmpty();

    }
}