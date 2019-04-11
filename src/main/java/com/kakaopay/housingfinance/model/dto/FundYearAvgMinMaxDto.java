package com.kakaopay.housingfinance.model.dto;

import lombok.*;

//@Data
//@Getter @Setter
//@Builder @NoArgsConstructor @AllArgsConstructor
@Value
public class FundYearAvgMinMaxDto {

    private final Integer year;

    private final Long amount;

}
