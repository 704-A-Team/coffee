package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.ProvideDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProvideDaoImpl implements ProvideDao {
	private final SqlSession session;

	@Override
	public int provideSave(ProvideDto provideDto) {
		System.out.println("ProvideDaoImpl provideSave start...");
		
		int provide_code = session.insert("provideSave", provideDto);
		
		return provide_code;
	}

	@Override
	public int totalProvide() {
		System.out.println("ProvideDaoImpl totalProvide start...");
		
		int totalProvideCount = session.selectOne("totalProvide");
		
		return totalProvideCount;
	}

	@Override
	public List<ProvideDto> provideList(ProvideDto provideDto) {
		System.out.println("ProvideDaoImpl provideList start...");
		
		List<ProvideDto> provideList = session.selectList("provideList", provideDto);
		
		return provideList;
	}

	@Override
	public ProvideDto provideDetail(int provide_code) {
		System.out.println("ProvideDaoImpl provideList start...");
		
		return session.selectOne("provideDetail", provide_code);
	}

	@Override
	public int provideModify(ProvideDto provideDto) {
		System.out.println("ProvideDaoImpl provideModify start...");
		
		return session.update("provideModify", provideDto);
	}

	@Override
	public void provideDelete(ProvideDto provideDetail) {
		System.out.println("ProvideDaoImpl provideDelete start...");
		
		session.update("provideDelete", provideDetail);
	}
	
	
}
