package com.kakaopay.housingfinance.common.exception;

import lombok.Getter;

@Getter
public enum  ApiExceptionMessage {

    FILE_NOT_FOUND_EXCEPTION("파일을 찾을 수 없습니다.");

    private String message;

    ApiExceptionMessage(String message){
        this.message = message;
    };
}
