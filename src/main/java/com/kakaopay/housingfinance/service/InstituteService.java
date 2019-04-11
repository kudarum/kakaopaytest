package com.kakaopay.housingfinance.service;

import com.kakaopay.housingfinance.model.Institute;
import com.kakaopay.housingfinance.repository.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituteService {

    @Autowired
    InstituteRepository instituteRepository;

    /**
     * 코트로 주택 공급기관 조회시 코드 오름차순 정렬
     * @return
     */
    public List<Institute> findAllOrderByCodeAsc() {
        Sort sort = new Sort(Sort.Direction.ASC,"code");
        return instituteRepository.findAll(sort);
    }



}
