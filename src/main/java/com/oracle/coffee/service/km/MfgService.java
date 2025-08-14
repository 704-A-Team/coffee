package com.oracle.coffee.service.km;

import java.util.List;

import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;

public interface MfgService {

	void 				mfgRegister(MfgDTO mfgDTO, List<MfgDetailDTO> mfgDetailDTO);

	String 				magamNext();

	int 				mfgTotal();

	List<MfgDTO> 		mfgList(MfgDTO mfgDTO);

	List<MfgDetailDTO> 	mfgDetail(MfgDetailDTO mfgDetailDTO);

	List<MfgDetailDTO> 	mfgStatus(MfgDetailDTO mfgDetailDTO);

	List<MfgDetailDTO> 	mfgUpdateForm(MfgDetailDTO mfgDetailDTO);
	
	void 				mfgInsert(MfgDetailDTO updateMfg);

	void 				mfgUpdate(MfgDetailDTO updateMfg);

	List<MfgDetailDTO> 	existingList(int mfg_code);

	void 				mfgDelete(MfgDetailDTO existingDTO);

	List<MfgDetailDTO> 	mfgApproveForm(MfgDetailDTO mfgDetailDTO);

	void 				mfgApproveUpdate(MfgDetailDTO mfgDetailDTO);

}
