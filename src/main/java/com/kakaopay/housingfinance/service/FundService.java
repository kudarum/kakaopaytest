package com.kakaopay.housingfinance.service;

import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.model.Institute;
import com.kakaopay.housingfinance.model.dto.*;
import com.kakaopay.housingfinance.repository.FundRepository;
import com.kakaopay.housingfinance.repository.InstituteRepository;
import com.kakaopay.housingfinance.repository.stats.FundStatsRepository;
import com.kakaopay.housingfinance.util.PredictionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
            throw new NullPointerException(ApiResponseMessage.ERROR_EMPTY_DATA.getMessage());
        }

        List<FundYearAvgMinMaxDto> fundYearAvgMinMaxDtoList = fundStatsRepository.findAllFundYearAvgMinMaxDtoList(institute_code);

        return FundYearAvgDto.builder()
                .bank(optionalInstitute.get().getName())
                .support_amount(fundYearAvgMinMaxDtoList)
                .build();
    }

}