package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oracle.coffee.dto.ProvideDto;

public interface ProvideDao {

	int 				provideSave(ProvideDto provideDto);
	int 				totalProvide();
	List<ProvideDto> 	provideList(ProvideDto provideDto);
	ProvideDto 			provideDetail(int provide_code);
	int					provideModify(ProvideDto provideDto);
	void 				provideDelete(ProvideDto provideDetail);
	ProvideDto 			getProvideInfo(ProvideDto provideDto);

}
