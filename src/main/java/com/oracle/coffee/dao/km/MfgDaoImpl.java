package com.oracle.coffee.dao.km;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;

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
		int mfgTotal = session.selectOne("mfgTotal");
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


}
