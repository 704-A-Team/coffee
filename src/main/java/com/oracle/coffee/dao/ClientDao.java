package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.SWClientDto;

public interface ClientDao {
	List<SWClientDto> 		getClientInfo(int client_type);

}
