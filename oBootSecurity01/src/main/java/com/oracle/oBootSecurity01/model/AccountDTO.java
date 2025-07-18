package com.oracle.oBootSecurity01.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
	
	private String id;
	private String username;
	private String password;
	private int	   age;
	private String roles;
}
