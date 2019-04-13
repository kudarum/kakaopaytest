package com.kakaopay.housingfinance.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.annotation.RequestScope;

@Getter @Setter
@RequestScope
public class ApiResponseBody<E> {

    private Integer code;

    private String message;

    private E result;

    @JsonIgnore
    private HttpStatus httpStatus;

    // 정상처리
    public ApiResponseBody(E result){
        this.code = HttpStatus.OK.value();
        this.message = ApiResponseMessage.RESPONSE_SUCCESS.getMessage();
        this.result = result;
    }

    // 정상처리
    public ApiResponseBody(Integer code, String message, E result){
        this.code = code;
        this.message = message;
        this.result = result;
    }

    // 기본형 메시지 설정.
    public ApiResponseBody(HttpStatus httpStatus, String message, E result){
        this.code = httpStatus.value();
        this.message = message;
        this.result = result;
    }

    // 실패형
    public ApiResponseBody(HttpStatus httpStatus){
        this.code = httpStatus.value();
        this.message = ApiResponseMessage.RESPONSE_FAIL.getMessage();
        this.result = null;
    }

    // 실패형
    public ApiResponseBody(HttpStatus httpStatus, String message){
        this.code = httpStatus.value();
        this.message = message;
        this.result = null;
    }

}
