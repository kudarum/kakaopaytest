package com.kakaopay.housingfinance.model.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class AccountDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
