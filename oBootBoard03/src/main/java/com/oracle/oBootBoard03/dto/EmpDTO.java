package com.oracle.oBootBoard03.dto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDTO {
	private int 		emp_no;
	private String		emp_id;
	private String		emp_password;
	private String 		emp_name;
	private String 		email;
	private String 		emp_tel;
	private long 		sal;
	private boolean 	del_status;
	private int 		dept_code;
	@Builder.Default
	private LocalDateTime in_date = LocalDateTime.now();
	
	// 조회용
	private String simage;  // 대표 사진 1장
	private String pageNum;
	private int start;
	private int end;
	// Page 정보
	private String currentPage;
	// File
	@Builder.Default
	private List<MultipartFile> file = new ArrayList<>();
	@Builder.Default
	private List<String> uploadFileNames = new ArrayList<>();
	
	public EmpDTO(Object[] empRow) {
	    this.emp_no       = ((Number) empRow[0]).intValue();         //  // 배열에 get() 없da!
	    this.emp_id       = (String) empRow[1];
	    this.emp_password = (String) empRow[2];
	    this.emp_name     = (String) empRow[3];
	    this.email        = (String) empRow[4];
	    this.emp_tel      = (String) empRow[5];
	    this.sal          = ((Number) empRow[6]).longValue();
	    this.del_status   = ((Number) empRow[7]).intValue() == 1;
	    this.dept_code    = ((Number) empRow[8]).intValue();
	    Timestamp date	  = (Timestamp) empRow[9];
	    this.in_date      = (date != null) ? date.toLocalDateTime() : null;
	    this.simage       = (String) empRow[10];
	}
}
