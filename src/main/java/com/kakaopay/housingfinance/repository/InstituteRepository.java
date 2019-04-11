package com.kakaopay.housingfinance.repository;

import com.kakaopay.housingfinance.model.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstituteRepository extends JpaRepository<Institute, Long> {

    // 코드로 주택 공급 기관 조회
    Optional<Institute> findByCode(String code);

    // 이름으로 주택 공급 기관 조회
    Optional<Institute> findByName(String institute_name);
}
