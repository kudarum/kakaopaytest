package com.kakaopay.housingfinance.util;


import com.workday.insights.timeseries.arima.Arima;
import com.workday.insights.timeseries.arima.struct.ArimaParams;
import com.workday.insights.timeseries.arima.struct.ForecastResult;

/**
 * 시계열 예측 Util
 */
public class PredictionUtil {

    public Double analysisArima(double[] arimaDataArray, Integer analysis_year, Integer analysis_month, Integer start_year, Integer start_month, Integer end_year, Integer end_month) throws Exception {

        try {

            if(arimaDataArray != null && arimaDataArray.length > 6) {

                int forecastSize = 1;

                //분석할 Size 구하기
                if(analysis_year < end_year || (analysis_year.equals(end_year) && analysis_month <= end_month)) {
                    // 검색 연도가 데이터의 최대 일자보다 작거나 같은 경우 => 이미 데이터가 존재 하는 경우
                    forecastSize = 1;
                } else if(analysis_year.equals(end_year) && analysis_month > end_month) {
                    forecastSize = analysis_month - end_month;
                } else {
                    // 검색 연도가 데이터의 최대 일자보다 큰경우 => 데이터가 없는 경우
                    forecastSize = (12 - end_month) + analysis_month;
                }

                // Set ARIMA model parameters.
                // Setting ARIMA(1,0,1) which work as a combination of AR and MA models
                int p = 1;
                int d = 0;
                int q = 1;

                int P = 1;
                int D = 1;
                int Q = 0;
                int m = 0;

                // setting the forecast size.
                ArimaParams arimaParams = new ArimaParams(p, d, q, P, D, Q, m);

                ForecastResult forecastResult = Arima.forecast_arima(arimaDataArray, forecastSize, arimaParams);

                double[] forecast = forecastResult.getForecast();

                return forecast[forecast.length-1];
            } else {
                return null;
            }

        } catch (Exception e) {
            throw e;
        }
    }
}
