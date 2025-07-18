package com.oracle.oBootSecurity01.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.oBootSecurity01.domain.Account;


public interface UserRepository extends JpaRepository<Account, Long> {
	//                            username 이 Uniqe해야 한다
	Account findByUsername(String username);

}
