package com.oracle.coffee.service.km;

import java.util.List;

import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;

public interface MfgService {

	void 			mfgRegister(MfgDTO mfgDTO, List<MfgDetailDTO> mfgDetailDTO);

	String 			magamNext();

	int 			mfgTotal();

	List<MfgDTO> 	mfgList(MfgDTO mfgDTO);

}
