package com.oracle.oBootBoard03.service;

import java.util.List;

import com.oracle.oBootBoard03.dto.BoardDTO;
import com.oracle.oBootBoard03.dto.PageRequestDTO;

public interface BoardService {
	int				totalBoard();
	List<BoardDTO> 	boardList(BoardDTO boardDTO);
	void 			Boardwrite(BoardDTO boardDTO);
	BoardDTO 		detail(BoardDTO boardDTO1);
}
