package com.kakaopay.housingfinance.repository.stats;

import com.kakaopay.housingfinance.model.dto.FundYearAvgMinMaxDto;
import com.kakaopay.housingfinance.model.dto.FundYearSumDto;
import com.kakaopay.housingfinance.model.dto.FundYearSumMaxDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@SuppressWarnings("ALL")
@Repository
public class FundStatsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<FundYearSumDto> findAllFundYearSum()
    {
        Query nativeQuery = entityManager.createNativeQuery(
                "select " +
                        "           result.year as year" +
                        "           , sum(result.support_amount) over(partition by result.year) as total_amount" +
                        "           , ins.institute_name as institute_name" +
                        "           , result.support_amount as support_amount" +
                        "   from(" +
                        "       select sta.year" +
                        "           , sta.institute_code" +
                        "           , sum(sta.amount) as support_amount" +
                        "       from Fund as sta" +
                        "       group by sta.year, sta.institute_code" +
                        "   ) as result" +
                        "       inner join institute as ins" +
                        "           on ins.institute_code = result.institute_code " +
                        "   order by result.year, ins.institute_code"
                ,"FundYearSumMapping"
        );

        return nativeQuery.getResultList();
    }

    public FundYearSumMaxDto findAllFundYearSumMax(){
        Query nativeQuery = entityManager.createNativeQuery(
                "SELECT result.year" +
                        "       , (SELECT ins.institute_code FROM Institute ins WHERE ins.institute_code = result.institute_code) as bank" +
                        "   FROM (" +
                        "       SELECT TOP 1 sta.year" +
                        "           , sta.institute_code" +
                        "           , SUM(sta.amount) total_amount" +
                        "       FROM Fund AS sta" +
                        "       GROUP BY sta.year, sta.institute_code" +
                        "       ORDER BY total_amount DESC" +
                        "   ) AS result"
                ,"FundYearSumMaxMapping"
        );

        return (FundYearSumMaxDto)nativeQuery.getSingleResult();
    }

    // amount의 경우 자동으로 반올림 처리 됨.
    public List<FundYearAvgMinMaxDto> findAllFundYearAvgMinMaxDtoList(String institute_code) {
        Query nativeQuery = entityManager.createNativeQuery(
                "SELECT RESULT.YEAR" +
                        "       , RESULT.amount AS amount" +
                        "   FROM ("+
                        "       SELECT res.year as year" +
                        "           , res.amount as amount" +
                        "           , ROW_NUMBER() OVER(ORDER BY res.amount DESC) as maxrow" +
                        "           , ROW_NUMBER() OVER(ORDER BY res.amount ASC) as minrow" +
                        "      FROM(" +
                        "           SELECT" +
                        "               sta.year" +
                        "               , sta.institute_code" +
                        "               , AVG(CAST(sta.amount as DECIMAL(10,2))) as amount" +
                        "           FROM Fund as sta" +
                        "           WHERE sta.institute_code = :institute_code " +
                        "           GROUP BY sta.year, sta.institute_code" +
                        "       ) AS res" +
                        "   ) AS RESULT" +
                        "   WHERE 1=1 " +
                        "       AND (RESULT.maxrow = 1 OR RESULT.minrow = 1)"
                ,"FundYearAvgMinMaxMapping"
        ).setParameter("institute_code",institute_code);
        return nativeQuery.getResultList();
    }
}
