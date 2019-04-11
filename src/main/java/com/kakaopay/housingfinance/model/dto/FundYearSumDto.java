package com.kakaopay.housingfinance.model.dto;
import lombok.*;

@Data
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class FundYearSumDto {

    private Integer year;

    private Integer total_amount;

    private String institute_name;

    private Long support_amount;
}
