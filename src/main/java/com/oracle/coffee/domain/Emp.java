package com.oracle.coffee.domain;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator (
		name = "emp_seq",
		sequenceName = "EMP_SEQ",
		initialValue = 2000,
		allocationSize = 1
		
		)

@EntityListeners(AuditingEntityListener.class) 
public class Emp {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "emp_seq"
			)
	private int emp_code;
	private String emp_name;
	private String emp_tel;
	private int emp_dept_code;
	private int emp_grade;
	private int emp_sal;
	private String emp_email;
	@ColumnDefault("0")		//default column 구분: 0-> 재직, 1-> 퇴직
	private int emp_isdel;
	private int emp_register;
	@CreatedDate
	private LocalDateTime emp_reg_date;
	private Date emp_ipsa_date;
	private Date emp_birth;
	
	public void changeEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public void changeEmp_tel(String emp_tel) {
		this.emp_tel = emp_tel;
	}
	public void changeEmp_dept_code(int emp_dept_code) {
		this.emp_dept_code = emp_dept_code;
	}
	public void changeEmp_grade(int emp_grade) {
		this.emp_grade = emp_grade;
	}
	public void changeEmp_sal(int emp_sal) {
		this.emp_sal = emp_sal;
	}
	public void changeEmp_email(String emp_email) {
		this.emp_email = emp_email;
	}
	public void changeEmp_isdel(int emp_isdel) {
		this.emp_isdel = emp_isdel;
	}
	public void changeEmp_register(int emp_register) {
		this.emp_register = emp_register;
	}
	public void changeEmp_reg_date(LocalDateTime emp_reg_date) {
		this.emp_reg_date = emp_reg_date;
	}
	public void changeEmp_ipsa_date(Date emp_ipsa_date) {
		this.emp_ipsa_date = emp_ipsa_date;
	}
	public void changeEmp_birth(Date emp_birth)	{
		this.emp_birth = emp_birth;
	}
	
}