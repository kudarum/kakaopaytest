package com.kakaopay.housingfinance.util;

import com.github.signaflo.timeseries.TimeSeries;
import com.github.signaflo.timeseries.Ts;
import com.github.signaflo.timeseries.forecast.Forecast;
import com.github.signaflo.timeseries.model.arima.Arima;
import com.github.signaflo.timeseries.model.arima.ArimaOrder;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 시계열 예측 Util
 */
@Slf4j
public class PredictionUtil {

    public Double analysisArima(double[] arimaDataArray, Integer analysis_year, Integer analysis_month, Integer start_year, Integer start_month, Integer end_year, Integer end_month) throws Exception {

        try {
            // validation check
            String check_result = arimaValidate(arimaDataArray, analysis_year, start_year, start_month, end_year, end_month);

            Integer search_fix_month;

            if(check_result.isEmpty()) {

                //분석할 Size 구하기
                if(analysis_year <= end_year) {
                    // 검색 연도가 데이터의 최대 일자보다 작거나 같은 경우 => 이미 데이터가 존재 하는 경우
                    search_fix_month = analysis_month + 1;
                } else {
                    // 검색 연도가 데이터의 최대 일자보다 큰경우 => 데이터가 없는 경우
                    search_fix_month = (12 - end_month) + analysis_month;
                }

                TimeSeries timeSeries = Ts.newMonthlySeries(start_year, start_month, arimaDataArray);

                ArimaOrder modelOrder = ArimaOrder.order(2, 0, 1, 1, 1, 1);

                com.github.signaflo.timeseries.model.arima.Arima model = Arima.model(timeSeries, modelOrder);

                log.debug("ARIMA AIC : " + model.aic()); // Get and display the model AIC

                Forecast forecast = model.forecast(search_fix_month); // To specify the alpha significance level, add it as a second argument.

                List<Double> forecastList = forecast.pointEstimates().asList();

                return forecastList.get(forecastList.size()-1);
            } else {
                throw new Exception(check_result);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private String arimaValidate(double[] arimaDataArray, Integer analysis_year, Integer start_year, Integer start_month, Integer end_year, Integer end_month) {

        String check_result = "";

        if((arimaDataArray == null || arimaDataArray.length == 0) || start_year == null || start_month == null || end_year == null
        || end_month == null || analysis_year < start_year || analysis_year > end_year+1) {
            check_result = ApiResponseMessage.ERROR_RUN_TIME_EXCEPTION.getMessage();
        }

        return check_result;

    }

}
