package com.oracle.coffee.dao.km;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.km.CheckApproveResultDTO;
import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;
import com.oracle.coffee.dto.km.MfgRpDTO;
import com.oracle.coffee.dto.km.MfgRpDetailDTO;
import com.oracle.coffee.dto.km.RpDetailDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@RequiredArgsConstructor
@Log4j2
public class MfgDaoImpl implements MfgDao {
	
	private final SqlSession session;

	@Override
	public void mfgSeqRegister(MfgDTO mfgDTO) {
		session.insert("mfgSeqRegister" , mfgDTO);
		
	}
	
	@Override
	public void mfgRegister(MfgDetailDTO mfgWanDetail) {
		session.insert("mfgRegister" , mfgWanDetail);
	}

	@Override
	public String magamNext() {
		String magamNext = session.selectOne("magamNext");
		System.out.println("magamNext->"+magamNext);
		return magamNext;
	}

	@Override
	public int mfgTotal() {
		int mfgTotal = 0;
		
		try {
			mfgTotal = session.selectOne("mfgTotal");;
			System.out.println("MfgDao mfgTotal() mfgTotal->"+mfgTotal);
			
		} catch (Exception e) {
			// System.out.println("MfgDao mfgTotal() e.getMessage() -> "+e.getMessage());
			 // ★ 변경: e.printStackTrace() 사용 ★
	        System.err.println("MfgDao mfgTotal() 오류 발생!"); // 오류임을 명확히 표시
	        e.printStackTrace(); // 예외의 모든 스택 트레이스 출력
	        // 더 좋은 방법은 예외를 다시 던져서 서비스/컨트롤러 계층에서 처리하도록 하는 것
	        throw new RuntimeException("mfgTotal 데이터 조회 중 오류가 발생했습니다.", e);
		}

		
		
		return mfgTotal;
	}

	@Override
	public List<MfgDTO> mfgList(MfgDTO mfgDTO) {
		List<MfgDTO> mfgList = session.selectList("mfgList", mfgDTO);
		return mfgList;
	}

	@Override
	public List<MfgDetailDTO> mfgDetail(MfgDetailDTO mfgDetailDTO) {
		List<MfgDetailDTO> mfgDetail = session.selectList("mfgDetail" , mfgDetailDTO);
		return mfgDetail;
	}

	@Override
	public List<MfgDetailDTO> mfgStatus(MfgDetailDTO mfgDetailDTO) {
		List<MfgDetailDTO> mfgStatus = session.selectList("mfgStatus" , mfgDetailDTO);
		return mfgStatus;
	}

	@Override
	public List<MfgDetailDTO> mfgUpdateForm(MfgDetailDTO mfgDetailDTO) {
		List<MfgDetailDTO> mfgUpdateForm = session.selectList("mfgUpdateForm" , mfgDetailDTO);
		return mfgUpdateForm;
	}

	@Override
	public List<MfgDetailDTO> existingList(int mfg_code) {
		List<MfgDetailDTO> existingList = session.selectList("existingList" , mfg_code);
		return existingList;
	}
	
	@Override
	public void mfgDelete(MfgDetailDTO existingDTO) {
		session.delete("mfgDelete" , existingDTO);
		
	}
	
	@Override
	public void mfgInsert(MfgDetailDTO updateMfg) {
		try {
			int rows = session.insert("mfgInsert" , updateMfg);
			if(rows == 0) {
				// 업데이트된 데이터가 없으면 예외 발생 (롤백 유도)
				throw new RuntimeException("업데이트 대상 없습니다 : "+ updateMfg.getMfg_code());
			}
		} catch (Exception e) {
			throw new RuntimeException("생산 신청 업데이트 실패 : " + updateMfg , e);
		}
		
	}

	@Override
	public void mfgUpdate(MfgDetailDTO updateMfg) {
		try {
			int rows = session.update("mfgUpdate" , updateMfg);
			if(rows == 0) {
				// 업데이트된 데이터가 없으면 예외 발생 (롤백 유도)
				throw new RuntimeException("업데이트 대상 없습니다 : "+ updateMfg.getMfg_code());
			}
		} catch (Exception e) {
			throw new RuntimeException("생산 신청 업데이트 실패 : " + updateMfg , e);
		}
		
	}

	@Override
	public List<MfgDetailDTO> mfgApproveForm(MfgDetailDTO mfgDetailDTO) {
		List<MfgDetailDTO> mfgApproveForm = session.selectList("mfgApproveForm" , mfgDetailDTO);
		return mfgApproveForm;
	}

	@Override
	public void mfgApproveUpdate(MfgDetailDTO mfgDetailDTO) {
		session.update("mfgApproveUpdate" , mfgDetailDTO);
		
	}

	@Override
	public int mfgRpTotal() {
		int mfgRpTotal = session.selectOne("mfgRpTotal");
		return mfgRpTotal;
	}

	@Override
	public List<MfgDetailDTO> mfgReportList(MfgDetailDTO mfgDetailDTO) {
		List<MfgDetailDTO> mfgReportList = session.selectList("mfgReportList" , mfgDetailDTO);
		return mfgReportList;
	}

	@Override
	public List<MfgRpDTO> mfgReportForm(MfgRpDTO mfgRpDTO) {
		List<MfgRpDTO> mfgReportForm = session.selectList("mfgReportForm" , mfgRpDTO);
		return mfgReportForm;
	}
	
	@Override
	public void mfgRpstatus(MfgRpDTO mfgRpDTO) {
		int result = session.update("mfgRpstatus" , mfgRpDTO);
		log.info("mfgRpstatus result->"+result);
	}

	@Override
	public void mfgRpSubmit(MfgRpDTO mfgRpDTO) {
		int result = session.insert("mfgRpSubmit" , mfgRpDTO);
		log.info("mfgRpSubmit result rows = {}" + result);
	}

	@Override
	public void rpDetailSubmit(RpDetailDTO rpDetailDTO) {
		int result = session.insert("rpDetailSubmit" , rpDetailDTO);
		log.info("rpDetailSubmit result rows = {}" + result);
	}

	@Override
	public List<MfgRpDetailDTO> mfgReportDetail(MfgRpDTO mfgRpDTO) {
		List<MfgRpDetailDTO> mfgReportDetail = session.selectList("mfgReportDetail" , mfgRpDTO);
		return mfgReportDetail;
	}

	@Override
	public void mfgRpUpdate(MfgRpDTO mfgRpDTO) {
		int result = session.update("mfgRpUpdate" , mfgRpDTO);
		log.info("mfgRpUpdate result rows = {}" + result);
		
	}

	@Override
	public void rpDetailUpdate(RpDetailDTO rpDetailDTO) {
		int result = session.insert("rpDetailUpdate" , rpDetailDTO);
		log.info("rpDetailUpdate result rows = {}" + result);
		
	}

	@Override
	public void checkApprove(MfgDetailDTO mfgDetailDTO) {
		// CheckApproveResultDTO checkApprove = session.selectOne("checkApprove", mfgDetailDTO); 
		session.selectOne("checkApprove", mfgDetailDTO); 
		System.out.println("MfgDaoImpl checkApprove mfgDetailDTO->"+mfgDetailDTO);
		System.out.println("MfgDaoImpl checkApprove mfgDetailDTO.getListWonCodeLackDTO->"+mfgDetailDTO.getListWonCodeLackDTO());
	//	return checkApprove;  
	}




}
