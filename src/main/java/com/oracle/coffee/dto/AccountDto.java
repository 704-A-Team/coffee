package com.oracle.coffee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
	   private Long   	id;
	   private int 		client_code;
	   private int 		emp_code;
	   private String 	username;
	   private String 	password;
	   private String 	roles;
	   private String 	displayName;


		public AccountDto(int Long, String username, String password, String roles) {
			this.username = username;
			this.password = password;
			this.client_code = client_code;
			this.emp_code = emp_code;
			this.roles = roles;
		}
}
