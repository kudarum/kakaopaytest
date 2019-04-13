package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.common.response.ApiResponseBody;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.model.dto.FundStatsDto;
import com.kakaopay.housingfinance.model.dto.FundStatsSearchDto;
import com.kakaopay.housingfinance.model.dto.FundYearAvgDto;
import com.kakaopay.housingfinance.model.dto.FundYearSumMaxDto;
import com.kakaopay.housingfinance.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/funds",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FundController {

    @Autowired
    FundService fundService;

    /**
     * 년도별 각 금융기관의 지원금액 합계
     * @return
     */
    @GetMapping("/stats/yearsum")
    public ResponseEntity getFundStatsYearSum() {

        FundStatsDto fundStatsYearSum = fundService.getFundStatsYearSum();

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBody<>(fundStatsYearSum));

    }

    /**
     * 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관 조회
     * @return
     */
    @GetMapping("/stats/yearsum/max")
    public ResponseEntity getFundStatsYearSumMax(){

        FundYearSumMaxDto fundYearSumMaxDto = fundService.getFundStatsYearSumMax();

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBody<>(fundYearSumMaxDto));
    }

    /**
     * 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회
     * @return
     */
    @GetMapping("/stats/yearavg/min-max")
    public ResponseEntity getFundStatsYearAvgMinMax(@RequestBody @Valid FundStatsSearchDto fundStatsSearchDto, Errors errors){

        if(errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponseBody<>(HttpStatus.BAD_REQUEST,ApiResponseMessage.ERROR_PARAM_NOT_FOUND.getMessage()));
        }

        FundYearAvgDto fundYearAvgDto = fundService.getFundStatsYearAvgMinMax(fundStatsSearchDto.getInstitute_code());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBody<>(fundYearAvgDto));
    }
}
