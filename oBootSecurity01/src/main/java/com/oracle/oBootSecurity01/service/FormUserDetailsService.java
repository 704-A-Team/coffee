package com.oracle.oBootSecurity01.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oracle.oBootSecurity01.domain.Account;
import com.oracle.oBootSecurity01.model.AccountContext;
import com.oracle.oBootSecurity01.model.AccountDTO;
import com.oracle.oBootSecurity01.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service("userDetailService")
@RequiredArgsConstructor
@Transactional
//UserDetails: Spring Security에서 사용자의 정보를 담는 인터페이스
//UserDetailsService: Spring Security에서 유저의 정보를 가져오는 인터페이스
public class FormUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = userRepository.findByUsername(username);
//		if(account == null) {
//			throw new 
//		}													권한을 가져온다(권한이 여러개 일 수 있다 -> List)
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(account.getRoles()));
		ModelMapper mapper = new ModelMapper();
		AccountDTO accountDTO = mapper.map(account, AccountDTO.class);
		System.out.println("FormUserDetailsService loadUserByUsername accountDTO->"+accountDTO);
		System.out.println("FormUserDetailsService loadUserByUsername authorities->"+authorities);
		//          UserDetail을 상속받았기 때문에 return 가능
		return new AccountContext(accountDTO, authorities);
	}

}
