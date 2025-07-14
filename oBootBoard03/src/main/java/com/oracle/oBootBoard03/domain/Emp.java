package com.oracle.oBootBoard03.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@SequenceGenerator(
					name = "emp_seq_gen",					// 객체
					sequenceName = "emp_seq_generator",		// DB
					initialValue = 1000000,
					allocationSize = 1
			)
public class Emp {
	@Id
	@GeneratedValue(
					strategy = GenerationType.SEQUENCE,
					generator = "emp_seq_gen"
			)
	private int 		emp_no;
	private String		emp_id;
	private String		emp_password;
	private String 		emp_name;
	private String 		email;
	private String 		emp_tel;
	private long 		sal;
	private boolean 	del_status;
	private int 		dept_code;
	private LocalDateTime 	in_date;
	// @ElementCollection => JPA에서 JOIN 없이도 가져올 수 있다
	@ElementCollection
	//         mappedBy = "N쪽 필드명", "나는 주인이 아니에요~"라고 표시
	//@OneToMany(mappedBy = "emp")
	@Builder.Default
	private List<EmpImage> imageList = new ArrayList<>();
	
	public void changeEmp_no(int emp_no) {
		this.emp_no = emp_no;
	}
	public void changeEmp_password(String emp_password) {
		this.emp_password = emp_password;
	}
	public void changeEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public void changeEmail(String email) {
		this.email = email;
	}
	public void changeEmp_tel(String emp_tel) {
		this.emp_tel = emp_tel;
	}
	public void changeSal(long sal) {
		this.sal = sal;
	}
	public void changeDel_status(boolean del_status) {
		this.del_status = del_status;
	}
	public void changeDept_code(int dept_code) {
		this.dept_code = dept_code;
	}
	
	public void imageAddString(String filename1) {
		// 빌더의 목적 : EmpImage 객체 만들기
		// List<String> file = EmpImage.builder() 틀리다
		EmpImage empImage = EmpImage.builder()
									.filename(filename1)
									.build()
									;
		imageAdd(empImage);
		
	}
	
	private void imageAdd(EmpImage empImage) {
		empImage.setOrder_num(this.imageList.size());
		imageList.add(empImage);
		
	}
	
	// clearList(): 모든 이미리를 리스트에서 제거
	public void clearList() {
		this.imageList.clear();
	}
	
	
}
