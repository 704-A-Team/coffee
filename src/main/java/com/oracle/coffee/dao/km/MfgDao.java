package com.oracle.coffee.dao.km;

import java.util.List;

import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;

public interface MfgDao {
	void 			mfgSeqRegister(MfgDTO mfgDTO);
	
	void 			mfgRegister(MfgDetailDTO mfgWanDetail);

	String 			magamNext();

	int 			mfgTotal();

	List<MfgDTO> 	mfgList(MfgDTO mfgDTO);

}
