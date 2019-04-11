package com.kakaopay.housingfinance.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kakaopay.housingfinance.model.serializer.FundStatsJsonSerializer;
import lombok.*;

import java.util.List;

@Data
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
@JsonSerialize(using = FundStatsJsonSerializer.class)
public class FundStatsDto {

    private String name;

    private List<FundYearSumDto> resultList;

}
