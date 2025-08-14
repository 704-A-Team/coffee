package com.oracle.coffee.service.km;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.coffee.dao.km.MfgDao;
import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MfgServiceImpl implements MfgService {
	
	private final MfgDao mfgDao;

	@Override
	public void mfgRegister(MfgDTO mfgDTO, List<MfgDetailDTO> mfgDetailDTO) {
		log.info("mfgRegister mfgDetailDTO->"+mfgDetailDTO);
		try {
			// 1. 생산 신청 등록 --> 신청 코드 받기
			mfgDao.mfgSeqRegister(mfgDTO);
			
			// 2. 생산 신청 Detail 등록
			for(MfgDetailDTO mfgWanDetail : mfgDetailDTO) {
				mfgWanDetail.setMfg_status(1);
				mfgWanDetail.setMfg_code(mfgDTO.getMfg_code());
				mfgDao.mfgRegister(mfgWanDetail);
			}
			
		} catch (Exception e) {
			 // RuntimeException으로 감싸서 다시 던지면 트랜잭션 롤백됨
			throw new RuntimeException("생산 신청 중 오류 발생" , e);
		}
		
	}

	@Override
	public String magamNext() {
		String magamNext = mfgDao.magamNext();
		return magamNext;
	}

	@Override
	public int mfgTotal() {
		int mfgTotal = 0;
		try {
			mfgTotal = mfgDao.mfgTotal();
		} catch (Exception e) {
			log.error("mfgTotal() 조회 중 오류 발생" , e);
			
		}
		return mfgTotal;
	}

	@Override
	public List<MfgDTO> mfgList(MfgDTO mfgDTO) {
		List<MfgDTO> mfgList = null;
		try {
			mfgList = mfgDao.mfgList(mfgDTO);
		} catch (Exception e) {
			log.error("mfgList() 조회 중 오류 발생" , e);
			
		}
		return mfgList;
	}

	@Override
	public List<MfgDetailDTO> mfgDetail(MfgDetailDTO mfgDetailDTO) {
		List<MfgDetailDTO> mfgDetail = null;
		try {
			mfgDetail = mfgDao.mfgDetail(mfgDetailDTO);
		} catch (Exception e) {
			log.error("mfgList() 조회 중 오류 발생" , e);
		}
		return mfgDetail;
	}

	@Override
	public List<MfgDetailDTO> mfgStatus(MfgDetailDTO mfgDetailDTO) {
		List<MfgDetailDTO> mfgStatus = null;
		try {
			mfgStatus = mfgDao.mfgStatus(mfgDetailDTO);
		} catch (Exception e) {
			log.error("mfgList() 조회 중 오류 발생" , e);
		}
		return mfgStatus;
	}

	@Override
	public List<MfgDetailDTO> mfgUpdateForm(MfgDetailDTO mfgDetailDTO) {
		List<MfgDetailDTO> mfgUpdateForm = null;
		try {
			mfgUpdateForm = mfgDao.mfgUpdateForm(mfgDetailDTO);
		} catch (Exception e) {
			log.error("mfgUpdateForm() 조회 중 오류 발생" , e);
		}
		return mfgUpdateForm;
	}
	
	@Override
	public List<MfgDetailDTO> existingList(int mfg_code) {
		List<MfgDetailDTO> existingList = mfgDao.existingList(mfg_code);
		log.info("existingList->"+existingList);
		return existingList;
	}

	@Override
	public void mfgDelete(MfgDetailDTO existingDTO) {
		log.info("mfgDelete->"+existingDTO);
		mfgDao.mfgDelete(existingDTO);
	}

	@Override
	public void mfgInsert(MfgDetailDTO updateMfg) {
		log.info("mfgInsert->"+updateMfg);
		mfgDao.mfgInsert(updateMfg);
		log.info("mfgInsert Service updateMfg->"+updateMfg);
	}

	@Override
	public void mfgUpdate(MfgDetailDTO updateMfg) {
		log.info("updateMfg->"+updateMfg);
		mfgDao.mfgUpdate(updateMfg);
		log.info("mfgUpdate Service updateMfg->"+updateMfg);
		
	}

	@Override
	public List<MfgDetailDTO> mfgApproveForm(MfgDetailDTO mfgDetailDTO) {
		List<MfgDetailDTO> mfgApproveForm = mfgDao.mfgApproveForm(mfgDetailDTO);
		log.info("mfgApproveForm List->"+mfgApproveForm);
		return mfgApproveForm;
	}

	@Override
	public void mfgApproveUpdate(MfgDetailDTO mfgDetailDTO) {
		mfgDao.mfgApproveUpdate(mfgDetailDTO);
		
	}

}
