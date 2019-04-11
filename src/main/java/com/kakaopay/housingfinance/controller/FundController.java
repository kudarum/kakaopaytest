package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.model.dto.PredictionFundDto;
import com.kakaopay.housingfinance.model.dto.FundStatsDto;
import com.kakaopay.housingfinance.model.dto.FundYearAvgDto;
import com.kakaopay.housingfinance.model.dto.FundYearSumMaxDto;
import com.kakaopay.housingfinance.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fund",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FundController {

    @Autowired
    FundService fundService;

    /**
     * 년도별 각 금융기관의 지원금액 합계
     * @return
     */
    @GetMapping("/stats/yearsum")
    public ResponseEntity getAmountStatsYearSum() {

        FundStatsDto fundStatsYearSum = fundService.getFundStatsYearSum();

        return ResponseEntity.status(HttpStatus.OK).body(fundStatsYearSum);
    }

    /**
     * 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관 조회
     * @return
     */
    @GetMapping("/stats/yearsum/max")
    public ResponseEntity getAmountStatsYearSumMax(){

        FundYearSumMaxDto fundStatsYearSumMax = fundService.getFundStatsYearSumMax();

        return ResponseEntity.status(HttpStatus.OK).body(fundStatsYearSumMax);
    }

    /**
     * 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회
     * @return
     */
    @GetMapping("/stats/yearavg/minmax")
    public ResponseEntity getAmountStatsYearAvgMinMax(){
        String institute_code = "bank008"; // 외한은행 코드

        FundYearAvgDto fundYearAvgDto = fundService.getFundStatsYearAvgMinMax(institute_code);

        return ResponseEntity.status(HttpStatus.OK).body(fundYearAvgDto);
    }

    /**
     * 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측
     * @param predictionFundDto
     * @return
     */
    @GetMapping(value = "/stats/month/prediction")
    public ResponseEntity getAmountStatsMonthPrediction(@RequestBody PredictionFundDto predictionFundDto){

        PredictionFundDto fundStatsMonthPrediction = fundService.getFundStatsMonthPrediction(predictionFundDto);

        return ResponseEntity.status(HttpStatus.OK).body(fundStatsMonthPrediction);
    }
}
