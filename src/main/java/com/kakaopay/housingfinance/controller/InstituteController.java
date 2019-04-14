package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.common.response.ApiResponseBody;
import com.kakaopay.housingfinance.model.Institute;
import com.kakaopay.housingfinance.service.InstituteService;
import com.kakaopay.housingfinance.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/institutes",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class InstituteController {

    @Autowired
    private InstituteService instituteService;

    @Autowired
    private NumberUtil numberUtil;

    /**
     * 주택금융 공급 금융기관(은행) 목록 조회.
     * @return
     */
    @GetMapping
    public ResponseEntity getInstituteList() {

        // 금융기관 목록 조회
        List<Institute> instituteList = instituteService.getInstituteAllList();

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBody<>(instituteList));
    }
}
