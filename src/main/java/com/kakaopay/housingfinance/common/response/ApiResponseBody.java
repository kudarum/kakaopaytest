package com.kakaopay.housingfinance.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.annotation.RequestScope;

@Getter @Setter
@RequestScope
public class ApiResponseBody<E> {

    private String status;

    private String message;

    private E result;

    private String errorCode;

    private String errorMessage;

    @JsonIgnore
    private HttpStatus httpStatus;

    // 정상처리
    public ApiResponseBody(E result){
        this.status = ApiResponseMessage.RESPONSE_SUCCESS_CODE.getMessage();
        this.message = ApiResponseMessage.RESPONSE_SUCCESS.getMessage();
        this.result = result;
        this.errorCode = "";
        this.errorMessage = "";
    }

    // 기본형 메시지 설정.
    public ApiResponseBody(String message, E result){
        this.status = ApiResponseMessage.RESPONSE_SUCCESS_CODE.getMessage();
        this.message = message;
        this.result = result;
        this.errorCode = "";
        this.errorMessage = "";
    }

    // 기본형
    public ApiResponseBody(String status, String message, E result){
        this.status = status;
        this.message = message;
        this.result = result;
        this.errorCode = "";
        this.errorMessage = "";
    }

    // 실패형
    public ApiResponseBody(HttpStatus httpStatus, String errorMessage){
        this.status = ApiResponseMessage.RESPONSE_FAIL_CODE.getMessage();
        this.message = ApiResponseMessage.RESPONSE_FAIL.getMessage();
        this.result = null;
        this.errorCode = String.valueOf(httpStatus.value());
        this.errorMessage = errorMessage;
    }

}
