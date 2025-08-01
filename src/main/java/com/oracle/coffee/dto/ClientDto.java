package com.oracle.coffee.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClientDto {
	
	private int 				client_code;			//거래처코드
	private String 				client_name;			//거래처명
	private String 				saup_num;				//사업자등록번호
	private String 				boss_name;				//대표자명 
	private boolean 			client_type;			//거래처유형(공급처/가맹점)
	private String 				client_address;			//주소
	private String 				client_tel;				//전화번호
	private int 				client_emp_code;		//사원코드(영업사원)
	private boolean				client_status;			//영업상태(영업중,휴업,폐점)
	private int 				client_reg_code;		//사원코드(등록자)
	private LocalDateTime 		date;					//등록일
		

}
