package com.kakaopay.housingfinance.model.dto;

import lombok.*;

@Data
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class FundYearSumMaxDto {

    private Integer year;

    private String bank;

}
