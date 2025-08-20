package com.oracle.coffee.service.km;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oracle.coffee.dao.km.MfgDao;
import com.oracle.coffee.dto.km.CheckApproveResultDTO;
import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;
import com.oracle.coffee.dto.km.MfgRpDTO;
import com.oracle.coffee.dto.km.MfgRpDetailDTO;
import com.oracle.coffee.dto.km.RpDetailDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MfgServiceImpl implements MfgService {
	
	private final MfgDao mfgDao;
	private final ProductService productService;

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
			log.error("Service mfgTotal() 조회 중 오류 발생" , e);
			
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

	@Override
	public int mfgRpTotal() {
		int mfgRpTotal = 0;
		try {
			mfgRpTotal = mfgDao.mfgRpTotal();
		} catch (Exception e) {
			log.error("mfgTotal() 조회 중 오류 발생" , e.getMessage());
			
		}
		return mfgRpTotal;
	}

	@Override
	public List<MfgDetailDTO> mfgReportList(MfgDetailDTO mfgDetailDTO) {
		List<MfgDetailDTO> mfgReportList = null;
		try {
			mfgReportList = mfgDao.mfgReportList(mfgDetailDTO);
		} catch (Exception e) {
			log.error("mfgReportList() 조회 중 오류 발생" , e);
			
		}
		return mfgReportList;
	}

	@Override
	public List<MfgRpDTO> mfgReportForm(MfgRpDTO mfgRpDTO) {
		List<MfgRpDTO> mfgReportForm = null;
		try {
			mfgReportForm = mfgDao.mfgReportForm(mfgRpDTO);
		} catch (Exception e) {
			log.error("mfgReportForm() 조회 중 오류 발생" , e.getMessage());
		}
		return mfgReportForm;
	}
	
	@Override
	public void mfgRpstatus(MfgRpDTO mfgRpDTO) {
		mfgDao.mfgRpstatus(mfgRpDTO);
		
	}

	@Override
	public void mfgRpSubmit(MfgRpDTO mfgRpDTO) {
		log.info("mfgRpSubmit mfgRpDTO->"+mfgRpDTO);
		
		// 소수점 둘째
		double yield = Math.round((double)mfgRpDTO.getMfg_end() / mfgRpDTO.getMfg_mat() * 100.0 * 100) / 100.0;
		mfgRpDTO.setYield(yield);
		System.out.println("yield->"+yield);
		
		// 순환참조 피하여 사용
		int t_yield = productService.findYield(mfgRpDTO.getProduct_code());
		double pct = yield - t_yield;
		mfgRpDTO.setPct(pct);
		System.out.println("pct->"+pct);
		
		mfgDao.mfgRpSubmit(mfgRpDTO);
		 
	}

	@Override
	public void rpDetailSubmit(List<RpDetailDTO> details) {
		
		for(RpDetailDTO rpDetailDTO : details) {
			mfgDao.rpDetailSubmit(rpDetailDTO);
			log.info("rpDetailSubmit rpDetailDTO->"+rpDetailDTO);
		}
		
	}

	@Override
	public List<MfgRpDetailDTO> mfgReportDetail(MfgRpDTO mfgRpDTO) {
		List<MfgRpDetailDTO> mfgReportDetail = mfgDao.mfgReportDetail(mfgRpDTO);
		return mfgReportDetail;
	}

	@Override
	public void mfgRpUpdate(MfgRpDTO mfgRpDTO) {
		log.info("mfgRpSubmit mfgRpDTO->"+mfgRpDTO);
		
		// 소수점 둘째
		double yield = Math.round((double)mfgRpDTO.getMfg_end() / mfgRpDTO.getMfg_mat() * 100.0 * 100) / 100.0;
		mfgRpDTO.setYield(yield);
		System.out.println("yield->"+yield);
		
		// 순환참조 피하여 사용
		int t_yield = productService.findYield(mfgRpDTO.getProduct_code());
		double pct = yield - t_yield;
		mfgRpDTO.setPct(pct);
		System.out.println("pct->"+pct);
		
		mfgDao.mfgRpUpdate(mfgRpDTO);
		
	}

	@Override
	public void rpDetailUpdate(List<RpDetailDTO> details) {
		for(RpDetailDTO rpDetailDTO : details) {
			mfgDao.rpDetailUpdate(rpDetailDTO);
			log.info("rpDetailUpdate rpDetailDTO->"+rpDetailDTO);
		}

	}

	@Override
	public void checkApprove(MfgDetailDTO mfgDetailDTO) {
	    mfgDao.checkApprove(mfgDetailDTO);
	//    return result;
	}


}
