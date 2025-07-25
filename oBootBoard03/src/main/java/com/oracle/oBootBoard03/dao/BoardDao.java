package com.oracle.oBootBoard03.dao;

import java.util.List;

import com.oracle.oBootBoard03.domain.Board;
import com.oracle.oBootBoard03.dto.BoardDTO;

public interface BoardDao {
	List<BoardDTO> 	boardListSel();
	int				insertBoard(BoardDTO boardDTO);
	int 			totalBoard();
	List<BoardDTO> 	boardList(BoardDTO boardDTO);
	BoardDTO 		detail(BoardDTO boardDTO1);
	void 			delete(int board_no);
}
