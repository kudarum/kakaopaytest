package com.kakaopay.housingfinance.common.response;

import lombok.Getter;

@Getter
public enum ApiResponseMessage {

    RESPONSE_SUCCESS_CODE("success"),
    RESPONSE_FAIL_CODE("fail"),

    RESPONSE_SUCCESS("정상 처리되었습니다."),
    RESPONSE_FAIL("처리에 실패하였습니다."),


    RESPONSE_SUCCESS_FILE("주택금융 데이터 업로드가 정상적으로 처리되었습니다."),

    ERROR_FILE_NOT_FOUND("파일을 찾을 수 없습니다."),
    ERROR_FILE_MAX_SIZE("파일이 너무 큽니다."),
    ERROR_FILE_PROCESS_FAIL("파일 업로드 처리에 실패하였습니다."),
    ERROR_EMPTY_DATA("데이터가 없습니다."),
    ERROR_RUN_TIME_EXCEPTION("서버에 오류가 발생하였습니다.")
    ;
    private String message;

    ApiResponseMessage(String message) {
        this.message = message;
    }
}
