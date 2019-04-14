package com.kakaopay.housingfinance.service;

import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.model.Account;
import com.kakaopay.housingfinance.model.adapter.AccountAdapter;
import com.kakaopay.housingfinance.repository.AccountRepository;
import com.kakaopay.housingfinance.util.JwtUtil;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    public Account saveAccount(Account account) {

        Account byUsername = accountRepository.findByUsername(account.getUsername());

        Account resultAccount = null;

        if(byUsername == null) {
            account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
            resultAccount = accountRepository.save(account);
        }

        return resultAccount;
    }

    public Account getAccountPasswordMatch(Account account) {

        Account getAccountInfo = accountRepository.findByUsername(account.getUsername());

        if(getAccountInfo != null) {
            if(bCryptPasswordEncoder.matches(account.getPassword(), getAccountInfo.getPassword())) {
                return getAccountInfo;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if(account == null) {
            throw new UsernameNotFoundException(ApiResponseMessage.ERROR_UNAUTHORIZED.getMessage());
        }
        return new AccountAdapter(account);
    }


    public String getRefreshToken(String authorizationToken) {
        authorizationToken = authorizationToken.replaceAll("Bearer", "").trim();

        if (authorizationToken.equals("")) {
            throw new UnsupportedJwtException(ApiResponseMessage.ERROR_NOT_FOUND_TOKEN.getMessage());
        }

        String refreshToken = jwtUtil.refreshJwt(authorizationToken);

        if (refreshToken.equals("")) {
            throw new UnsupportedJwtException(ApiResponseMessage.ERROR_NOT_RESOLVE_TOKEN.getMessage());
        }

        return refreshToken;
    }
}
