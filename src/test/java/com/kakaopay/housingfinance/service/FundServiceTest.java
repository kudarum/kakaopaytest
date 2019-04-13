package com.kakaopay.housingfinance.service;

import com.kakaopay.housingfinance.cmm.BaseTest;
import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.model.dto.FundStatsDto;
import com.kakaopay.housingfinance.model.dto.FundYearAvgDto;
import com.kakaopay.housingfinance.model.dto.FundYearSumMaxDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


public class FundServiceTest extends BaseTest {

    @Autowired
    FundService fundService;

    @Before
    public void setUp() throws Exception {
        fundDataDelete();
        fundDataInsert();
    }

    @Test
    @TestDescription("Service 테스트 - 년도별 각 금융기관의 지원금액 합계 조회")
    public void test_GetFundStatsYearSum() {
        FundStatsDto fundStatsYearSum = fundService.getFundStatsYearSum();

        assertThat(fundStatsYearSum).isNotNull();
        assertThat(fundStatsYearSum.getResultList().size()).isGreaterThan(0);
    }

    @Test
    @TestDescription("Service 테스트 - 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관 조회")
    public void getFundStatsYearSumMax() {

        FundYearSumMaxDto fundStatsYearSumMax = fundService.getFundStatsYearSumMax();

        assertThat(fundStatsYearSumMax).isNotNull();
        assertThat(fundStatsYearSumMax.getBank()).isNotNull();
        assertThat(fundStatsYearSumMax.getYear()).isNotNull();

    }

    @Test
    @TestDescription("Service 테스트 - 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회")
    public void getFundStatsYearAvgMinMax() {

        String institute_code = "bank008";
        FundYearAvgDto fundStatsYearAvgMinMax = fundService.getFundStatsYearAvgMinMax(institute_code);
        assertThat(fundStatsYearAvgMinMax).isNotNull();
        assertThat(fundStatsYearAvgMinMax).isNotNull();

    }
}