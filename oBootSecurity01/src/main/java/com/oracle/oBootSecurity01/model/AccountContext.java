package com.oracle.oBootSecurity01.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class AccountContext implements UserDetails {
	
	private AccountDTO accountDTO;
	private final List<GrantedAuthority> roles;
	
	public AccountContext(AccountDTO accountDTO, List<GrantedAuthority> roles) {
		this.accountDTO = accountDTO;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return accountDTO.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return accountDTO.getUsername();
	}
	
	@Override
	public boolean isEnabled() {
		// 어떤 값을 받아서 사용하지 못하게 한다
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// 시간이 지나면 쓸 수 없게 한다
		return true;
	}

}
