package com.kakaopay.housingfinance.config;

import com.kakaopay.housingfinance.model.Institute;
import com.kakaopay.housingfinance.repository.InstituteRepository;
import com.kakaopay.housingfinance.util.CsvUtil;
import com.kakaopay.housingfinance.util.NumberUtil;
import com.kakaopay.housingfinance.util.PredictionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // CSV 변환 UTIL
    @Bean
    public CsvUtil csvUtil(){
        return new CsvUtil();
    }

    // Number Format 관련 UTIL
    @Bean
    public NumberUtil numberUtil(){
        return new NumberUtil();
    }

    // 시계열 예측 UTIL
    @Bean
    PredictionUtil predictionUtil(){
        return new PredictionUtil();
    }

    // 앱 시작 시 동작하며 기관의 초기데이터 생성.
    @Bean
    public ApplicationRunner applicationRunner(){
        return new ApplicationRunner() {

            @Autowired
            MessageSource messageSource;


            @Autowired
            InstituteRepository instituteRepository;

            @Override
            public void run(ApplicationArguments args) throws Exception {

                List<Institute> instituteList = new ArrayList<>();
                String[] institute_array = {"주택도시기금", "국민은행", "우리은행", "신한은행", "한국시티은행", "하나은행", "농협은행/수협은행", "외환은행", "기타은행"};
                int code_count = 1;

                for(String institute_name : institute_array){
                    Institute institute = Institute.builder()
                            .name(institute_name)
                            .code("bank00"+code_count)
                            .build();
                    instituteList.add(institute);
                    code_count++;
                }

                instituteRepository.saveAll(instituteList);

            }
        };
    }
}
