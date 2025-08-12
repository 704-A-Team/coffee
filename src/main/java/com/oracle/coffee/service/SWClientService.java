package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.SWClientDto;

public interface SWClientService {
	List<SWClientDto> 		getClientsByProduct(int product_code);
	List<SWClientDto> 		clientIsList(SWClientDto swclientDto);

}
