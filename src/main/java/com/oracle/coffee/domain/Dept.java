package com.oracle.coffee.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/*import jakarta.persistence.Column;*/
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
		name = "dept_seq",
		sequenceName = "DEPT_SEQ",
		initialValue = 1000,
		allocationSize =  1
		
		)
@EntityListeners(AuditingEntityListener.class) // Auditing 리스너 활성화 -> LocalDate 기본값
public class Dept {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "dept_seq"
			)
	private int 				dept_code;
	private String 				dept_name;
	private String 				dept_tel;
	@ColumnDefault("0")			
	//default column 구분: 0->부서 존재, 1->부서 사라짐
	private Boolean 			dept_isdel;
	@CreatedDate					
	//실제 시간 대입 
	private LocalDateTime	 	dept_reg_date;
	
	
	public void changeDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public void changeDept_tel(String dept_tel) {
		this.dept_tel = dept_tel;
	}
	public void changeDept_isdel(boolean dept_isdel) {
		this.dept_isdel = dept_isdel;
	}
	public void changeDept_reg_date(LocalDateTime dept_reg_date) {
		this.dept_reg_date = dept_reg_date;
	}
	
}
