package com.kakaopay.housingfinance.util;

import com.github.signaflo.timeseries.TimeSeries;
import com.github.signaflo.timeseries.Ts;
import com.github.signaflo.timeseries.forecast.Forecast;
import com.github.signaflo.timeseries.model.arima.Arima;
import com.github.signaflo.timeseries.model.arima.ArimaOrder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 시계열 예측 Util
 */
@Slf4j
public class PredictionUtil {

    public Double analysisARIMA(double[] arimaDataArray, Integer analysisYear, Integer analysisMonth, Integer startYear, Integer startMonth, Integer endYear, Integer endMonth){

        // validation check
        String checkResult = arimaValidate(arimaDataArray, analysisYear, startYear, startMonth, endYear, endMonth);

        Integer searchFixMonth;

        if(checkResult.isEmpty()) {

            //분석할 Size 구하기
            if(analysisYear <= endYear) {
                // 검색 연도가 데이터의 최대 일자보다 작거나 같은 경우 => 이미 데이터가 존재 하는 경우
                searchFixMonth = analysisMonth + 1;
            } else {
                // 검색 연도가 데이터의 최대 일자보다 큰경우 => 데이터가 없는 경우
                searchFixMonth = (12 - endMonth) + analysisMonth;
            }

            TimeSeries timeSeries = Ts.newMonthlySeries(startYear, startMonth, arimaDataArray);

            ArimaOrder modelOrder = ArimaOrder.order(2, 0, 1, 1, 1, 1);

            com.github.signaflo.timeseries.model.arima.Arima model = Arima.model(timeSeries, modelOrder);

            log.debug("ARIMA AIC : " + model.aic()); // Get and display the model AIC

            Forecast forecast = model.forecast(searchFixMonth); // To specify the alpha significance level, add it as a second argument.

            List<Double> forecastList = forecast.pointEstimates().asList();

            return forecastList.get(forecastList.size()-1);
        }

        return null;

    }

    private String arimaValidate(double[] arimaDataArray, Integer analysisYear, Integer startYear, Integer startMonth, Integer endYear, Integer endMonth) {

        String result = "";

        if(arimaDataArray == null || arimaDataArray.length == 0) {
            // data empty error
            log.error("ARIMA VALIDATE : arimaDataArray is empty");
            result = "e";
        }

        if(startYear == null) {
            log.error("ARIMA VALIDATE : startYear is null");
            result = "e";
        }

        if(startMonth == null) {
            log.error("ARIMA VALIDATE : startMonth is null");
            result = "e";
        }

        if(endYear == null) {
            log.error("ARIMA VALIDATE : endYear is null");
            result = "e";
        }

        if(endMonth == null) {
            log.error("ARIMA VALIDATE : endMonth is null");
            result = "e";
        }

        if(analysisYear < startYear) {
            log.error("ARIMA VALIDATE : analysisYear is too many past");
        }

        // 데이터보다 2년 이상 뒤의 년도를 분석하려는 경우
        if(analysisYear > endYear+1 ) {
            log.error("ARIMA VALIDATE : analysisYear is too many future");
            result = "e";
        }

        return result;

    }

}
