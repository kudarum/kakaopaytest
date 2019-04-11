package com.kakaopay.housingfinance.model;

import com.kakaopay.housingfinance.model.dto.FundYearAvgMinMaxDto;
import com.kakaopay.housingfinance.model.dto.FundYearSumDto;
import com.kakaopay.housingfinance.model.dto.FundYearSumMaxDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@SqlResultSetMappings({
        @SqlResultSetMapping(name = "FundYearSumMapping", classes = {
                @ConstructorResult(
                        targetClass = FundYearSumDto.class,
                        columns = {
                                @ColumnResult(name = "year", type = Integer.class),
                                @ColumnResult(name = "total_amount", type = Integer.class),
                                @ColumnResult(name = "institute_name", type = String.class),
                                @ColumnResult(name = "support_amount", type = Long.class),
                        }
                )
        }),
        @SqlResultSetMapping(name = "FundYearSumMaxMapping", classes = {
                @ConstructorResult(
                        targetClass = FundYearSumMaxDto.class,
                        columns = {
                                @ColumnResult(name = "year", type = Integer.class),
                                @ColumnResult(name = "bank", type = String.class),
                        }
                )
        }),
        @SqlResultSetMapping(name = "FundYearAvgMinMaxMapping", classes = {
                @ConstructorResult(
                        targetClass = FundYearAvgMinMaxDto.class,
                        columns = {
                                @ColumnResult(name = "year", type = Integer.class),
                                @ColumnResult(name = "amount", type = Long.class)
                        }
                )
        })


})

@Entity
@Table(name = "FUND")
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Fund {

    @Id @GeneratedValue
    private Long id;

    @Min(0)
    private Integer year;

    @Min(0)
    private Integer month;

    @NotEmpty
    @Column(name="institute_code")
    private String code;

    @Min(0)
    private Long amount;

}
