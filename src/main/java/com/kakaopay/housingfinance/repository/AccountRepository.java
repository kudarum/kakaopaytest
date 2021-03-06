package com.kakaopay.housingfinance.repository;

import com.kakaopay.housingfinance.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
}
