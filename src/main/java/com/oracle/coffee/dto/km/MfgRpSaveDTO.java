package com.oracle.coffee.dto.km;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;	

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MfgRpSaveDTO {
	
	private MfgRpDTO mfgRpDTO;			// 생산보고 기본정보
	
	private List<RpDetailDTO> details;	// 생산 원재료 정보

}
