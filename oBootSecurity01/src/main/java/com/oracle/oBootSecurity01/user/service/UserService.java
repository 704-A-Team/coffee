package com.oracle.oBootSecurity01.user.service;

import org.springframework.stereotype.Service;

import com.oracle.oBootSecurity01.domain.Account;
import com.oracle.oBootSecurity01.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	
	private final UserRepository userRepository;
	
	@Transactional
	public void createUser(Account account) {
		userRepository.save(account);
	}
}
