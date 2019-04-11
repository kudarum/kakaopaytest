package com.kakaopay.housingfinance.model.dto;

import lombok.*;

import java.util.List;

@Data
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class FundYearAvgDto {
    private String bank;

    private List<FundYearAvgMinMaxDto> support_amount;
}
