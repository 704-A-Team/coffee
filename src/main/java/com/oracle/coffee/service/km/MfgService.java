package com.oracle.coffee.service.km;

import java.util.List;
import java.util.Map;

import com.oracle.coffee.dto.km.CheckApproveResultDTO;
import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;
import com.oracle.coffee.dto.km.MfgRpDTO;
import com.oracle.coffee.dto.km.MfgRpDetailDTO;
import com.oracle.coffee.dto.km.RpDetailDTO;

public interface MfgService {

	void 					mfgRegister(MfgDTO mfgDTO, List<MfgDetailDTO> mfgDetailDTO);

	String 					magamNext();

	int 					mfgTotal();

	List<MfgDTO> 			mfgList(MfgDTO mfgDTO);

	List<MfgDetailDTO> 		mfgDetail(MfgDetailDTO mfgDetailDTO);

	List<MfgDetailDTO> 		mfgStatus(MfgDetailDTO mfgDetailDTO);

	List<MfgDetailDTO> 		mfgUpdateForm(MfgDetailDTO mfgDetailDTO);
	
	void 					mfgInsert(MfgDetailDTO updateMfg);

	void 					mfgUpdate(MfgDetailDTO updateMfg);

	List<MfgDetailDTO> 		existingList(int mfg_code);

	void 					mfgDelete(MfgDetailDTO existingDTO);

	List<MfgDetailDTO> 		mfgApproveForm(MfgDetailDTO mfgDetailDTO);

	void 					mfgApproveUpdate(MfgDetailDTO mfgDetailDTO);

	int 					mfgRpTotal();

	List<MfgDetailDTO> 		mfgReportList(MfgDetailDTO mfgDetailDTO);

	List<MfgRpDTO> 			mfgReportForm(MfgRpDTO mfgRpDTO);

	void 					mfgRpSubmit(MfgRpDTO mfgRpDTO);

	void 					rpDetailSubmit(List<RpDetailDTO> details);

	void 					mfgRpstatus(MfgRpDTO mfgRpDTO);

	List<MfgRpDetailDTO>   	mfgReportDetail(MfgRpDTO mfgRpDTO);

	void 					mfgRpUpdate(MfgRpDTO mfgRpDTO);

	void 					rpDetailUpdate(List<RpDetailDTO> details);

	void 	checkApprove(MfgDetailDTO mfgDetailDTO);
	

}
