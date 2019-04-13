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

}
