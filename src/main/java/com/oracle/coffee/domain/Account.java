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

// Serializable 사용이유 
// 웹 애플리케이션에서 사용자 세션 정보를 안전하게 관리하고, 
// 서버 재시작이나 클러스터 환경에서도 세션이 유지될 수 있도록 하기 위함
@Entity
@Data
@SequenceGenerator(
		name = "ACCOUNT_SEQ",  // Seq 객체
		sequenceName = "ACCOUNT_SEQ", // Seq DB 
		initialValue = 4001,
		allocationSize = 1
  )
public class Account implements Serializable {
	@Id
	@GeneratedValue(
			 strategy = GenerationType.SEQUENCE,
			 generator = "ACCOUNT_SEQ"
	)
    @Column(name = "id")
    private Long    id; 	
	private Integer emp_code;
	private Integer client_code;
    private String username;
    private String password;
    private String roles;

}

