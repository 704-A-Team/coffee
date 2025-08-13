package com.oracle.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.coffee.dao.ClientDao;
import com.oracle.coffee.dto.SWClientDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class SWClientServiceImpl implements SWClientService {
	
	private final ClientDao		clientDao;

	@Override
	public List<SWClientDto> getClientsByProduct(int product_code) {
		System.out.println("SWClientServiceImpl getClientsByProduct start...");
		
		return clientDao.getClientsByProduct(product_code);
	}

	@Override
	public List<SWClientDto> clientIsList(SWClientDto swclientDto) {
		System.out.println("SWClientServiceImpl clientIsList start...");
		
		return clientDao.clientIsList(swclientDto);
	}

}
