package com.oracle.coffee.domain;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client_tb")
@SequenceGenerator (
		name = "client_seq",
		sequenceName = "CLIENT_SEQ",
		initialValue = 3000,
		allocationSize = 1
		
		)

@EntityListeners(AuditingEntityListener.class) 
public class Client {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "client_seq"
			)
	private int client_code;
	private String client_name;
	private String saup_num;
	private String boss_name;
	private int client_type;
	private String client_address;
	private String client_tel;
	private int client_emp_code;
	private int client_status;
	private int client_reg_code;
	@CreatedDate
	private LocalDateTime client_reg_date;
	
	public void changeClient_name(String client_name) {
		this.client_name = client_name;
	}
	public void changeSaup_num(String saup_num) {
		this.saup_num = saup_num;
	}
	public void changeBoss_name(String boss_name) {
		this.boss_name = boss_name;
	}
	public void changeClient_type(int client_type) {
		this.client_type = client_type;
	}
	public void changeClient_address(String client_address) {
		this.client_address = client_address;
	}
	public void changeClient_tel(String client_tel) {
		this.client_tel = client_tel;
	}
	public void changeClient_emp_code(int client_emp_code) {
		this.client_emp_code = client_emp_code;
	}
	public void changeClient_status(int client_status) {
		this.client_status = client_status;
	}
	public void changeClient_reg_code(int client_reg_code) {
		this.client_reg_code = client_reg_code;
	}
	public void changeClient_reg_date(LocalDateTime client_reg_date) {
		this.client_reg_date = client_reg_date;
	}
	

	
}
