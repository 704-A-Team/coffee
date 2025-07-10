package com.oracle.oBootBoard03.dao;

import java.util.List;

import com.oracle.oBootBoard03.dto.BoardDTO;

public interface BoardDao {
	List<BoardDTO> 	boardListSel();
	int				insertBoard(BoardDTO boardDTO);
}
