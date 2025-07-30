package com.oracle.coffee.dto;

import java.time.LocalDateTime;

import com.oracle.coffee.domain.Emp;

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
	private boolean 			emp_isDel; 					//퇴직여부
	private int 				emp_register; 				//등록자(사원코드)
	private LocalDateTime 		emp_reg_date; 				//등록일
	private String				dept_code;					//joint한 부서코드					
    
	//직급
	public String 				getEmp_grade_detail() {
        
    		switch (this.emp_grade) {
            
            case 2: return "과장";
            case 3: return "부장";
            case 4: return "이사";
            case 5: return "사장";
            case 6: return "시스템";
            default: return "사원";
        }
    }
	
	public String getEmp_dept_code_detail() {
		
		switch (this.emp_dept_code) {
		
		case 1000: return "영업팀";
		case 1001: return "생산관리팀";
		case 1002: return "재고팀";
		case 1003: return "인사팀";
		case 1004: return "구매팀";
		case 1005: return "판매팀";
		default: return null;
		
		}
	}
	
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
		this.emp_isDel = emp.getEmp_isdel();
		this.emp_register = emp.getEmp_register();
		this.emp_reg_date = emp.getEmp_reg_date();
		
	
							}						
	

}
