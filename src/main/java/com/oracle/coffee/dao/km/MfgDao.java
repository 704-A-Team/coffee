package com.oracle.coffee.dao.km;

import java.util.List;
import java.util.Map;

import com.oracle.coffee.dto.km.CheckApproveResultDTO;
import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;
import com.oracle.coffee.dto.km.MfgRpDTO;
import com.oracle.coffee.dto.km.MfgRpDetailDTO;
import com.oracle.coffee.dto.km.RpDetailDTO;

public interface MfgDao {
	void 					mfgSeqRegister(MfgDTO mfgDTO);
	
	void 					mfgRegister(MfgDetailDTO mfgWanDetail);

	String 					magamNext();

	int 					mfgTotal();

	List<MfgDTO> 			mfgList(MfgDTO mfgDTO);

	List<MfgDetailDTO> 		mfgDetail(MfgDetailDTO mfgDetailDTO);

	List<MfgDetailDTO> 		mfgStatus(MfgDetailDTO mfgDetailDTO);

	List<MfgDetailDTO> 		mfgUpdateForm(MfgDetailDTO mfgDetailDTO);
	
	List<MfgDetailDTO> 		existingList(int mfg_code);

	void 					mfgDelete(MfgDetailDTO existingDTO);
	
	void 					mfgInsert(MfgDetailDTO updateMfg);

	void 					mfgUpdate(MfgDetailDTO updateMfg);

	List<MfgDetailDTO> 		mfgApproveForm(MfgDetailDTO mfgDetailDTO);

	void 					mfgApproveUpdate(MfgDetailDTO mfgDetailDTO);

	int 					mfgRpTotal();

	List<MfgDetailDTO> 		mfgReportList(MfgDetailDTO mfgDetailDTO);

	List<MfgRpDTO> 			mfgReportForm(MfgRpDTO mfgRpDTO);

	void 					mfgRpSubmit(MfgRpDTO mfgRpDTO);

	void 					rpDetailSubmit(RpDetailDTO rpDetailDTO);

	void 					mfgRpstatus(MfgRpDTO mfgRpDTO);

	List<MfgRpDetailDTO>  	mfgReportDetail(MfgRpDTO mfgRpDTO);

	void  					mfgRpUpdate(MfgRpDTO mfgRpDTO);

	void 					rpDetailUpdate(RpDetailDTO rpDetailDTO);

	void					checkApprove(MfgDetailDTO mfgDetailDTO);
 
}
