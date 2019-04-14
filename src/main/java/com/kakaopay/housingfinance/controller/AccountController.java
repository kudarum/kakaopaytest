package com.kakaopay.housingfinance.controller;

import com.kakaopay.housingfinance.common.response.ApiResponseBody;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.model.Account;
import com.kakaopay.housingfinance.model.AccountRole;
import com.kakaopay.housingfinance.model.dto.AccountDto;
import com.kakaopay.housingfinance.service.AccountService;
import com.kakaopay.housingfinance.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 회원 가입.
     * @param accountDto
     * @param errors
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity saveAccount(@RequestBody @Valid AccountDto accountDto, Errors errors){

        if(errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseBody<>(HttpStatus.BAD_REQUEST,ApiResponseMessage.ERROR_PARAM_NOT_FOUND.getMessage()));
        }

        Account account = modelMapper.map(accountDto, Account.class);
        List<AccountRole> roles = new ArrayList<>();
        roles.add(AccountRole.USER);
        account.setRoles(roles);

        Account resultAccount = accountService.saveAccount(account);

        if(resultAccount != null) {

            String jwt = jwtUtil.createJwt(resultAccount.getUsername(),resultAccount.getRoles());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseBody<>(jwt));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseBody<>(HttpStatus.BAD_REQUEST,ApiResponseMessage.ERROR_DUPLICATE_ACCOUNT.getMessage()));
        }



    }

    /**
     * 토큰 발급요청
     * @param accountDto
     * @param errors
     * @return
     */
    @PostMapping("/token")
    public ResponseEntity postAccountToken(@RequestBody @Valid AccountDto accountDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseBody<>(HttpStatus.BAD_REQUEST,ApiResponseMessage.ERROR_PARAM_NOT_FOUND.getMessage()));
        }

        Account account = accountService.getAccountPasswordMatch(modelMapper.map(accountDto,Account.class));

        if(account != null) {
            String token = jwtUtil.createJwt(account.getUsername(),account.getRoles());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseBody<>(HttpStatus.OK,ApiResponseMessage.RESPONSE_SUCCESS.getMessage(),token));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseBody<>(HttpStatus.BAD_REQUEST,ApiResponseMessage.ERROR_LOGIN_FAIL.getMessage()));
        }




    }

    /**
     * 토큰 재발급 요청
     * @param request
     * @return
     */
    @GetMapping("/token")
    public ResponseEntity getRefreshToken(HttpServletRequest request) {

        if (request.getHeader("Authorization") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseBody<>(HttpStatus.BAD_REQUEST,ApiResponseMessage.ERROR_PARAM_NOT_FOUND.getMessage()));
        }

        String authorization = accountService.getRefreshToken(request.getHeader("Authorization"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseBody<>(authorization));
    }





}
