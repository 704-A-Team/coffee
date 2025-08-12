package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.InventoryDto;
import com.oracle.coffee.dto.MagamDto;

public interface JHMagamDao {

	int totalMagamDao();

	List<MagamDto> magamList(MagamDto magamDto);

	List<MagamDto> monthMagamList(MagamDto magamDto);

	int countMonMagAll(MagamDto magamDto);

}
