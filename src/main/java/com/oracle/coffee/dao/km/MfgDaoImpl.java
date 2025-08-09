package com.oracle.coffee.dao.km;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
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

}
