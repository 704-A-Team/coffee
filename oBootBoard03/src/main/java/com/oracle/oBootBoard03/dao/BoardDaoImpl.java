package com.oracle.oBootBoard03.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootBoard03.dto.BoardDTO;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class BoardDaoImpl implements BoardDao {
	
	private final SqlSession session;

	@Override
	public List<BoardDTO> boardListSel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertBoard(BoardDTO boardDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

}
