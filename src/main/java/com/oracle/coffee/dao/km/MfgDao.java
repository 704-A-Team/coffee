package com.oracle.coffee.dao.km;

import java.util.List;

import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;

public interface MfgDao {
	void 				mfgSeqRegister(MfgDTO mfgDTO);
	
	void 				mfgRegister(MfgDetailDTO mfgWanDetail);

	String 				magamNext();

	int 				mfgTotal();

	List<MfgDTO> 		mfgList(MfgDTO mfgDTO);

	List<MfgDetailDTO> 	mfgDetail(MfgDetailDTO mfgDetailDTO);

	List<MfgDetailDTO> 	mfgStatus(MfgDetailDTO mfgDetailDTO);

	List<MfgDetailDTO> 	mfgUpdateForm(MfgDetailDTO mfgDetailDTO);
	
	List<MfgDetailDTO> 	existingList(int mfg_code);

	void 				mfgDelete(MfgDetailDTO existingDTO);
	
	void 				mfgInsert(MfgDetailDTO updateMfg);

	void 				mfgUpdate(MfgDetailDTO updateMfg);

	List<MfgDetailDTO> 	mfgApproveForm(MfgDetailDTO mfgDetailDTO);

	void 				mfgApproveUpdate(MfgDetailDTO mfgDetailDTO);
 
}
