package com.kakaopay.housingfinance.repository;

import com.kakaopay.housingfinance.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PredictionRepository extends JpaRepository<Fund, Long> {

    @Query(value = "SELECT RESULT.amount as amount" +
        "   FROM (" +
        "       SELECT" +
        "               sta.year as year" +
        "               , sta.month as month " +
        "               , sta.amount as amount" +
        "       FROM Fund as sta" +
        "       WHERE sta.institute_code = :institute_code" +
        "           AND sta.year < :analysis_year " +
        "   UNION ALL" +
        "       SELECT" +
        "               sta.year as year" +
        "               , sta.month as month " +
        "               , sta.amount as amount" +
        "       FROM Fund as sta" +
        "       WHERE sta.institute_code = :institute_code" +
        "           AND sta.year = :analysis_year " +
        "           AND sta.month < :analysis_month " +
        "   ) as RESULT" +
        "   ORDER BY RESULT.year asc, RESULT.month asc "
        ,nativeQuery = true
    )
    double[] getAllFundMonthPrediction(@Param("institute_code") String institute_code, @Param("analysis_year")Integer analysis_year, @Param("analysis_month")Integer analysis_month);
}
