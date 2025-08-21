package com.oracle.coffee.domain;

import java.io.Serializable;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
@SequenceGenerator(
		name = "ACCOUNT_SEQ", 		 
		sequenceName = "ACCOUNT_SEQ",  
		initialValue = 4001,
		allocationSize = 1
  )

//계정
public class Account implements Serializable {
	@Id
	@GeneratedValue(
			 strategy = GenerationType.SEQUENCE,
			 generator = "ACCOUNT_SEQ"
	)
    @Column(name = "id")
    private Long    id; 	
	private Integer emp_code;			//int가 아닌 Integer 쓴 이유
	private Integer client_code;		//emp_code 혹은 client_code 둘중 하나는 반드시 null로 받아야해서
    private String username;
    private String password;
    private String roles;

}

