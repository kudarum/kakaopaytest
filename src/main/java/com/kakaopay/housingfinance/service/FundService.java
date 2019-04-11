package com.kakaopay.housingfinance.service;

import com.kakaopay.housingfinance.model.Institute;
import com.kakaopay.housingfinance.model.dto.*;
import com.kakaopay.housingfinance.repository.InstituteRepository;
import com.kakaopay.housingfinance.repository.FundRepository;
import com.kakaopay.housingfinance.repository.stats.FundStatsRepository;
import com.kakaopay.housingfinance.util.PredictionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FundService {

    @Autowired
    InstituteRepository instituteRepository;

    @Autowired
    FundRepository fundRepository;

    @Autowired
    FundStatsRepository fundStatsRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PredictionUtil predictionUtil;

    // 년도별 각 금융기관의 지원금액 합계
    public FundStatsDto getFundStatsYearSum() {

        List<FundYearSumDto> fundYearSumDtoList = fundStatsRepository.findAllFundYearSum();

        return FundStatsDto.builder()
                .name("주택금융 공급현황")
                .resultList(fundYearSumDtoList)
                .build();
    }

    // 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관 조회
    public FundYearSumMaxDto getFundStatsYearSumMax() {
        return fundStatsRepository.findAllFundYearSumMax();
    }

    // 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액 조회
    public FundYearAvgDto getFundStatsYearAvgMinMax(String institute_code) {

        Optional<Institute> optionalInstitute = instituteRepository.findByCode(institute_code);

        // null이라면. optionalInstitute.isPresent() null이 아닐 때 => 그것의 반대이니 null인경우
        if(!optionalInstitute.isPresent()){
            // return null 오류.
        }

        List<FundYearAvgMinMaxDto> fundYearAvgMinMaxDtoList = fundStatsRepository.findAllFundYearAvgMinMaxDtoList(institute_code);


        return FundYearAvgDto.builder()
                .bank(optionalInstitute.get().getName())
                .support_amount(fundYearAvgMinMaxDtoList)
                .build();
    }

    // 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측
    @Transactional
    public PredictionFundDto getFundStatsMonthPrediction(PredictionFundDto predictionFundDto) {

        // 기관 코드 조회
        Optional<Institute> optionalInstitute = instituteRepository.findByName(predictionFundDto.getBank());

        if(!optionalInstitute.isPresent()){
            // return null 오류
        }

        String institute_code = optionalInstitute.get().getCode();
        Integer analysisYear = predictionFundDto.getYear();
        Integer analysisMonth = predictionFundDto.getMonth();

        // 현재 데이터의 시작 년월과 종료 년월 조회
        List<Map<String,Object>> minMaxYearMonth = fundRepository.getFirstSearchYearMaxMonth(analysisYear);

        if(minMaxYearMonth == null) {
            // return null 오류
        }

        Integer startYear = (Integer) minMaxYearMonth.get(0).get("year");
        Integer startMonth = (Integer) minMaxYearMonth.get(0).get("month");
        Integer endYear = (Integer) minMaxYearMonth.get(1).get("year");
        Integer endMonth = (Integer) minMaxYearMonth.get(1).get("month");

        // 조회 연도가 없는 경우.
        if(analysisYear == null){
            analysisYear = endYear + 1;
        }

        // 분석할 데이터 조회.
        double[] fundDataArray = fundRepository.getAllAmountStatsMonthPrediction(institute_code, analysisYear, analysisMonth);

        // ARIMA 시계열 예측 사용
        Double resultData = predictionUtil.analysisARIMA(fundDataArray, analysisYear, analysisMonth, startYear, startMonth, endYear, endMonth);

        if(resultData == null) {
            // 분석 실패
        }

        return PredictionFundDto.builder()
                .bank(institute_code)
                .year(analysisYear)
                .amount(Math.round(resultData))
                .month(predictionFundDto.getMonth())
                .build();

    }

}