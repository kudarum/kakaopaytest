package com.kakaopay.housingfinance.model.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class PredictionDto {

    @NotEmpty
    private String bank;

    private Integer year;

    @Min(1) @Max(12)
    private Integer month;

    private Long amount;

}
