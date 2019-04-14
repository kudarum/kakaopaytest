package com.kakaopay.housingfinance.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiResponseMessage {
    RESPONSE_SUCCESS("정상 처리되었습니다."),
    RESPONSE_FAIL("처리에 실패하였습니다."),
    RESPONSE_ACCOUNT_CREATED("%s 계정이 생성되었습니다."),
    RESPONSE_JWT_CREATED("토큰이 발급되었습니다."),

    RESPONSE_SUCCESS_FILE("주택금융 데이터 업로드가 정상적으로 처리되었습니다."),

    ERROR_DUPLICATE_ACCOUNT("이미 가입된 회원입니다."),
    ERROR_NOT_FOUND_TOKEN("요청 토큰이 없습니다."),
    ERROR_UNAUTHORIZED("올바른 요청이 아닙니다."),
    ERROR_LOGIN_FAIL("아이디와 비밀번호를 확인해주세요."),
    ERROR_NOT_RESOLVE_TOKEN("올바른 형태의 토큰이 아닙니다."),
    ERROR_PARAM_NOT_FOUND("필수 파라미터가 누락되었습니다."),
    ERROR_FILE_NOT_FOUND("파일을 찾을 수 없습니다."),
    ERROR_FILE_MAX_SIZE("파일이 너무 큽니다."),
    ERROR_FILE_PROCESS_FAIL("파일 업로드 처리에 실패하였습니다."),
    ERROR_EMPTY_DATA("데이터가 없습니다."),
    ERROR_RUN_TIME_EXCEPTION("서버에 오류가 발생하였습니다."),
    ERROR_PREDICTION_FAIL("예측데이터 수집이 미비하여, 예측이 불가능합니다"),
    ERROR_PREDICTION_FAIL_MONTH("%s 개월의 예측데이터가 부족하여, 예측이 불가능합니다"),

    RESPONSE_TITLE_YEAR_SUM("주택금융 공급현황");
    private String message;

    ApiResponseMessage(String message) {
        this.message = message;
    }
}