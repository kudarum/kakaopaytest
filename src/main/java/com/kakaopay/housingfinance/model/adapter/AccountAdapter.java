package com.kakaopay.housingfinance.model.adapter;

import com.kakaopay.housingfinance.model.Account;
import com.kakaopay.housingfinance.model.AccountRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AccountAdapter extends User {
    private Account account;

    public AccountAdapter(Account account) {
        super(account.getUsername(), account.getPassword(), authorities(account.getRoles()));
        this.account = account;
    }

    private static Collection<? extends GrantedAuthority> authorities(List<AccountRole> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_"+r.name())).collect(Collectors.toSet());
    }

    public Account getAccount() {
        return account;
    }
}
