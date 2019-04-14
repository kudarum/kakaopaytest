package com.kakaopay.housingfinance.model.dto;

import lombok.*;

@Data
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class FundYearAvgMinMaxDto {

    private Integer year;

    private Long amount;

}
