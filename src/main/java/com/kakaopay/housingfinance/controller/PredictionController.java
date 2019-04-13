package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.common.response.ApiResponseBody;
import com.kakaopay.housingfinance.model.dto.PredictionDto;
import com.kakaopay.housingfinance.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/predictions",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PredictionController {

    @Autowired
    PredictionService predictionService;

    /**
     * 특정 은행의 특정 달에 대해서 검색 년도 해당 달에 금융지원 금액을 예측
     * @param predictionDto
     * @return
     */
    @GetMapping("/fund/month")
    public ResponseEntity getPredictionFundMonth(@RequestBody PredictionDto predictionDto) throws Exception{

        PredictionDto predictionFundMonth = predictionService.getPredictionFundMonth(predictionDto);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBody<>(predictionFundMonth));
    }
}
