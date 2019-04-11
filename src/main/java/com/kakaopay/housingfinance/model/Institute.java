package com.kakaopay.housingfinance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * 금융 기관 Entity
 */
@Entity
@Table(name = "INSTITUTE")
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Institute {

    // 금융 기관 ID
    @Id @GeneratedValue @JsonIgnore
    private Long id;

    // 금융 기관 명
    @NotEmpty
    @Column(name = "institute_name")
    private String name;

    // 금융 기관 코드
    @NotEmpty
    @Column(name = "institute_code")
    private String code;

}
