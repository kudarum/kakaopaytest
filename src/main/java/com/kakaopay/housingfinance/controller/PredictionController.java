package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.common.response.ApiResponseBody;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.model.dto.PredictionDto;
import com.kakaopay.housingfinance.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity getPredictionFundMonth(@RequestBody @Valid PredictionDto predictionDto, Errors errors) throws Exception{

        if(errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponseBody<>(HttpStatus.BAD_REQUEST,ApiResponseMessage.ERROR_PARAM_NOT_FOUND.getMessage()));
        }

        PredictionDto predictionFundMonth = predictionService.getPredictionFundMonth(predictionDto);

        ApiResponseBody<PredictionDto> predictionDtoApiResponseBody;

        if(predictionFundMonth.getAmount() == null) {
            // 커스텀 오류 코드
            predictionDtoApiResponseBody = new ApiResponseBody<>(999,ApiResponseMessage.ERROR_PREDICTION_FAIL.getMessage(),predictionFundMonth);
        } else {
            predictionDtoApiResponseBody = new ApiResponseBody<>(predictionFundMonth);
        }

        return ResponseEntity.status(HttpStatus.OK).body(predictionDtoApiResponseBody);
    }
}
