package com.oracle.coffee.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SWClientDto {
	private int 			client_code;
	private String			client_name;
	private String			saup_num;
	private String			boss_name;
	private int				client_type;
	private String			address;
	private String			tel;
	private int				client_emp_code;
	private int				client_status;
	private int				client_reg_code;
	private LocalDateTime	client_reg_date;
	
	// 조회용
	private String      pageNum;  
	private int 		start; 		 	   
	private int 		end;
	private String      currentPage;
}
