package com.oracle.coffee.service.km;

import java.util.List;

import com.oracle.coffee.dto.km.BoardDTO;

public interface BoardService {

	void 				boardWrite(BoardDTO boardDTO);

	int 				boardTotal();

	List<BoardDTO> 		boardList(BoardDTO boardDTO);
	
	void 				upReadCount(BoardDTO boardDTO1);

	BoardDTO 			boardView(BoardDTO boardDTO1);

	void 				boardUpdate(BoardDTO boardDTO);

	void 				boardDelete(BoardDTO boardDTO);
	
}
