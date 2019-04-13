package com.kakaopay.housingfinance.model.dto;

import lombok.*;

@Data
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class PredictionDto {

    private String bank;

    private Integer year;

    private Integer month;

    private Long amount;

}
