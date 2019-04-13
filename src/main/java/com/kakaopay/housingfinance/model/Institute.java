package com.kakaopay.housingfinance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * 금융 기관 Entity
 *
 */
@Entity
@Table(name = "INSTITUTE"
    , uniqueConstraints = @UniqueConstraint(columnNames = {"institute_code"})
    ,indexes = {
        @Index(name = "IDX_INSTITUTE_01", unique = true, columnList = "institute_code")
        , @Index(name = "IDX_INSTITUTE_02", unique = false, columnList = "institute_name")
    })
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