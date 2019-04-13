package com.kakaopay.housingfinance.service;

import com.kakaopay.housingfinance.cmm.BaseTest;
import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.model.dto.PredictionDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class PredictionServiceTest extends BaseTest {

    @Autowired
    PredictionService predictionService;

    @Before
    public void setUp() throws Exception{
        // 데이터 모두 삭제
        fundDataDelete();

        // 입력된 데이터가 없을 떄.
        // 초기 데이터 생성후
        fundDataInsert();

    }

    @Test
    @TestDescription("ARIMA 예측 프로젝트 수행 - 7개월 이상의 데이터가 있을 경우 조회 성공")
    public void test_GetPredictionFundMonth() throws Exception {
        PredictionDto predictionDto = PredictionDto.builder()
                .year(2018)
                .month(2)
                .bank("국민은행")
                .build();

        PredictionDto predictionFundMonth = predictionService.getPredictionFundMonth(predictionDto);

        assertThat(predictionFundMonth).isNotNull();

    }

    @Test
    @TestDescription("ARIMA 예측 프로젝트 수행 - 7개월 미만의 데이터가 있을 경우 예측 불가능 상태")
    public void test_GetPredictionFundMonth_LackData() throws Exception {
        PredictionDto predictionDto = PredictionDto.builder()
                .year(2005)
                .month(6)
                .bank("국민은행")
                .build();

        PredictionDto predictionFundMonth = predictionService.getPredictionFundMonth(predictionDto);

        assertThat(predictionFundMonth.getAmount()).isNull();

    }

    @Test
    @TestDescription("ARIMA 예측 프로젝트 수행 - 7개월 미만의 데이터가 있을 경우 예측 불가능 상태")
    public void test_GetPredictionFundMonth_LackData2() throws Exception {
        PredictionDto predictionDto = PredictionDto.builder()
                .year(2005)
                .month(1)
                .bank("국민은행")
                .build();

        PredictionDto predictionFundMonth = predictionService.getPredictionFundMonth(predictionDto);

        assertThat(predictionFundMonth.getAmount()).isNull();

    }

}