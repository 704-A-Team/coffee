package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.MagamDto;

public interface JHMagamService {
	int 			totalMagamDto();
	List<MagamDto> 	magamList(MagamDto magamDto);

	

}
