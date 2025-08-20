package com.oracle.coffee.dto.km;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CheckApproveResultDTO {
	   private int result_status; // 0: 부족, 1: 충분
	   private List<WonCodeLackDTO> wonCodeLackList = new ArrayList<>();

}
