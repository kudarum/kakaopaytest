package com.kakaopay.housingfinance.model.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class FundStatsSearchDto {

    @NotEmpty
    private String institute_code;
}
