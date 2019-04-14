package com.kakaopay.housingfinance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "ACCOUNT"
        ,uniqueConstraints = @UniqueConstraint(columnNames = {"username"})
        ,indexes = {
            @Index(name = "IDX_FUND_01", unique = true, columnList = "username")
})
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Account implements UserDetails {

    @Id @GeneratedValue @JsonIgnore
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    // 하나의 enum이 아니라 여러개의 enum 을 가질 수 있기 때문에
    // 가져올 롤이 적고 거의 매번 접근 할 때마다 가져와야하기 때문에 EAGER 로 설정
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private List<AccountRole> roles = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_"+r.name())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
