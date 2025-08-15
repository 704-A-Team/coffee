package com.oracle.coffee.dto;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.oracle.coffee.domain.Emp;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpDto {
	
	private int 				emp_code; 					//사원코드
	private String				emp_name; 					//사원명
	private String 				emp_tel; 					//사원전화번호
	private int 				emp_dept_code;				//부서코드
	private int 				emp_grade;					//직급
	private int 				emp_sal; 					//급여
	private String 				emp_email; 					//이메일
	private int			 		emp_isdel; 					//퇴직여부
	private int 				emp_register; 				//등록자(사원코드)
	private LocalDateTime 		emp_reg_date; 				//등록일		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date				emp_ipsa_date;				//입사일
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date 				emp_birth;					//생일
	
	//join
	private String				dept_code;					//부서코드
	private String 				emp_grade_detail;			//직급분류
    
	

	// 페이징 관련 
	private String      pageNum;  
	private int 		start; 		 	   
	private int 		end;
	
	//페이지
	private String      currentPage;
	
	
	public EmpDto(Emp emp) {
		this.emp_code = emp.getEmp_code();
		this.emp_name = emp.getEmp_name();
		this.emp_tel = emp.getEmp_tel();
		this.emp_dept_code = emp.getEmp_dept_code();
		this.emp_grade = emp.getEmp_grade();
		this.emp_sal = emp.getEmp_sal();
		this.emp_email = emp.getEmp_email();
		this.emp_isdel = emp.getEmp_isdel();
		this.emp_register = emp.getEmp_register();
		this.emp_reg_date = emp.getEmp_reg_date();
		this.emp_ipsa_date = emp.getEmp_ipsa_date();
		this.emp_birth = emp.getEmp_birth();
	
		}
	
		//등록일 yyyy-MM-dd로 변경
		public String getEmpRegDateFormatted() {
			if (emp_reg_date != null) {
				return emp_reg_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			} else {
				return "";
			}
		}
		
		public String getEmpBirthFormatted() {
		    if (emp_birth != null) {
		        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(emp_birth);
		    }
		    return "";
		}

		public String getEmpIpsaDateFormatted() {
		    if (emp_ipsa_date != null) {
		        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(emp_ipsa_date);
		    }
		    return "";
		}

		

}
