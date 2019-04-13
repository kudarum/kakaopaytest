package com.kakaopay.housingfinance.util;

import java.text.DecimalFormat;

/**
 * 숫자 포멧 Util
 */
public class NumberUtil {

    /**
     * Number Format 콤마 제거
     * @param number
     * @return
     */
    public Long removeFormatNumberLong(String number) {
        try {
            return Long.parseLong(number.replaceAll(",",""));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Number Fromat 콤마 생성.
     * @param number
     * @return
     */
    public String appendFormatNumberLong(Long number) {
        try {
            return new DecimalFormat("#,###").format(number);
        } catch (Exception e) {
            throw e;
        }
    }
}
