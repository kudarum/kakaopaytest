package com.kakaopay.housingfinance.service;

import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.model.Institute;
import com.kakaopay.housingfinance.model.dto.PredictionDto;
import com.kakaopay.housingfinance.repository.FundRepository;
import com.kakaopay.housingfinance.repository.InstituteRepository;
import com.kakaopay.housingfinance.repository.PredictionRepository;
import com.kakaopay.housingfinance.util.PredictionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private PredictionUtil predictionUtil;

    // 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측
    @Transactional
    public PredictionDto getPredictionFundMonth(PredictionDto predictionFundDto) throws Exception {

        // 기관 코드 조회
        Institute institute = instituteRepository.findByName(predictionFundDto.getBank());

        if(institute == null){
            throw new NullPointerException(ApiResponseMessage.ERROR_PREDICTION_FAIL.getMessage());
        }

        String institute_code = institute.getCode();
        Integer analysis_year = predictionFundDto.getYear();
        Integer analysis_month = predictionFundDto.getMonth();

        // 현재 데이터의 시작 년월과 종료 년월 조회
        List<Map<String,Object>> minMaxYearMonth = fundRepository.getFirstSearchYearMaxMonth(analysis_year);

        if(minMaxYearMonth == null || minMaxYearMonth.size() == 0) {
            // return null 오류
            throw new NullPointerException(ApiResponseMessage.ERROR_PREDICTION_FAIL.getMessage());
        }

        Integer start_year = (Integer) minMaxYearMonth.get(0).get("year");
        Integer start_month = (Integer) minMaxYearMonth.get(0).get("month");
        Integer end_year = (Integer) minMaxYearMonth.get(1).get("year");
        Integer end_month = (Integer) minMaxYearMonth.get(1).get("month");

        // 조회 연도가 없는 경우. 조회 데이터의 마지막 연도 +1 한 연도의 데이터 예측
        if(analysis_year == null){
            analysis_year = end_year + 1;
        }

        // 분석할 데이터 조회.
        double[] fundDataArray = predictionRepository.getAllFundMonthPrediction(institute_code, analysis_year, analysis_month);

        // ARIMA 시계열 예측 사용
        Double resultData = predictionUtil.analysisArima(fundDataArray, analysis_year, analysis_month, start_year, start_month, end_year, end_month);

        if(resultData != null) {
            return PredictionDto.builder()
                    .bank(institute_code)
                    .year(analysis_year)
                    .amount(Math.round(resultData))
                    .month(predictionFundDto.getMonth())
                    .build();
        }

        return PredictionDto.builder()
            .bank(institute_code)
            .year(analysis_year)
            .month(predictionFundDto.getMonth())
            .build();

    }
}
