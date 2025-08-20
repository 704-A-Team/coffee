package com.oracle.coffee.dao.km;

import java.util.List;

import com.oracle.coffee.dto.km.BoardDTO;

public interface BoardDao {

	void 				boardWrite(BoardDTO boardDTO);

	int 				boardTotal();

	List<BoardDTO> 		boardList(BoardDTO boardDTO);

	BoardDTO 			boardView(BoardDTO boardDTO);

	void 				boardUpdate(BoardDTO boardDTO);

	void 				boardDelete(BoardDTO boardDTO);

	void 				upReadCount(BoardDTO boardDTO1);

}
