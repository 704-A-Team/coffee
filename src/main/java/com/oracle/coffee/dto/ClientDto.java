package com.oracle.coffee.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.oracle.coffee.domain.Client;

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
	private int		 			client_type;			//거래처유형(공급처/가맹점)
	private String 				client_address;			//주소
	private String 				client_tel;				//전화번호
	private int 				client_emp_code;		//사원코드(영업사원) - FK
	private int					client_status;			//영업상태(영업중,휴업,폐점)
	private int 				client_reg_code;		//사원코드(등록자)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime 		client_reg_date;		//등록일
	
	//join
	private String client_emp_name;						//join해서 가져올 사원 이름 
	private String client_emp_tel;						//join해서 가져올 사원 번호 
	private String client_type_br;					//join해서 가져올 거래처 유형 
		


	// 페이징 관련 
	private String      pageNum;  
	private int 		start; 		 	   
	private int 		end;
	
	//페이지
	private String      currentPage;
	
	
	public ClientDto(Client client) {
		this.client_code = client.getClient_code();
		this.client_name = client.getClient_name();
		this.saup_num = client.getSaup_num();
		this.boss_name = client.getBoss_name();
		this.client_type = client.getClient_type();
		this.client_address = client.getClient_address();
		this.client_tel = client.getClient_tel();
		this.client_emp_code = client.getClient_emp_code();
		this.client_status = client.getClient_status();
		this.client_reg_code = client.getClient_reg_code();
		this.client_reg_date = client.getClient_reg_date();	
	
		}
	
	//날짜 패턴 yyyy-MM-dd로 변경
			public String getClientRegDateFormatted() {
				if (client_reg_date != null) {
					return client_reg_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				} else {
					return "";
				}
			}
		

}
