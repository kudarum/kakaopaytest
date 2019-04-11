package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.model.Institute;
import com.kakaopay.housingfinance.service.InstituteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/institute",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class InstituteController {

    @Autowired
    InstituteService instituteService;

    /**
     * 주택금융 공급 금융기관(은행) 목록 조회.
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity getInstituteList(){

        // 금융기관 목록 조회
        List<Institute> instituteList = instituteService.findAllOrderByCodeAsc();

        return ResponseEntity.status(HttpStatus.OK).body(instituteList);
    }
}
