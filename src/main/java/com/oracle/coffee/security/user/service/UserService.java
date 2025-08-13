package com.oracle.coffee.security.user.service;

import org.springframework.stereotype.Service;

import com.oracle.coffee.security.user.repository.UserRepository;
import com.oracle.coffee.domain.Account;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	
	@Transactional
	public void createUser(Account account) {
		userRepository.save(account);
	}

	public Long totalAccount() {
        Long totalCount = userRepository.count();
		return totalCount;
	}

	
}
