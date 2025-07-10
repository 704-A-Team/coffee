package com.oracle.oBootBoard03.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootBoard03.dao.BoardDao;
import com.oracle.oBootBoard03.dto.BoardDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardDao boardDao;

	@Override
	public int totalBoard() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BoardDTO> boardList(BoardDTO boardDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
