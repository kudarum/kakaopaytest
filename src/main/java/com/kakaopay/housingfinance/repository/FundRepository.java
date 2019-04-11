package com.kakaopay.housingfinance.repository;

import com.kakaopay.housingfinance.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public interface FundRepository extends JpaRepository<Fund, Long> {

    /**
     *
     * @param year
     * @return
     */
    @Query(value = "SELECT RESULT.year" +
            "               , CASE WHEN RESULT.min_row = 1 THEN RESULT.minMonth" +
            "                       WHEN RESULT.max_row = 1 THEN RESULT.maxMonth" +
            "               END AS month" +
            "           FROM(" +
            "           SELECT sta.year" +
            "               , MAX(sta.month) AS maxMonth" +
            "               , MIN(sta.month) AS minMonth" +
            "               , ROW_NUMBER() OVER(ORDER BY sta.year ASC) as min_row" +
            "               , ROW_NUMBER() OVER(ORDER BY sta.year DESC) as max_row" +
            "           FROM Fund AS sta" +
            "           GROUP BY sta.year" +
            "           ORDER BY sta.year ASC" +
            "       ) AS RESULT" +
            "       WHERE 1=1 AND (RESULT.min_row = 1 OR RESULT.max_row = 1)"
            , nativeQuery = true)
    List<Map<String,Object>> getFirstSearchYearMaxMonth(Integer year);

    /**
     * 예측에 필요한 데이터 조회
     * @param institute_code : 조회할 기관 코드
     * @param analysisYear : 예측 조회할 연도
     * @param analysisMonth : 예측 조회할 월 (단. 기존 데이터가 있는 년월를 조회할 경우 해당 년월 미만 까지만 조회)
     * @return
     */
    @Query(value ="SELECT RESULT.amount" +
            "   FROM (" +
            "       SELECT" +
            "               sta.year as year" +
            "               , sta.month as month " +
            "               , sta.amount as amount" +
            "       FROM Fund as sta" +
            "       WHERE sta.institute_code = :institute_code" +
            "           AND sta.year < :analysisYear " +
            "   UNION ALL" +
            "       SELECT" +
            "               sta.year as year" +
            "               , sta.month as month " +
            "               , sta.amount as amount" +
            "       FROM Fund as sta" +
            "       WHERE sta.institute_code = :institute_code" +
            "           AND sta.year = :analysisYear " +
            "           AND sta.month < :analysisMonth " +
            "   ) as RESULT" +
            "   ORDER BY RESULT.year asc, RESULT.month asc "
            , nativeQuery = true)
    double[] getAllAmountStatsMonthPrediction(@Param("institute_code") String institute_code, @Param("analysisYear") Integer analysisYear, @Param("analysisMonth") Integer analysisMonth);


}
