package com.oracle.oBootBoard03.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		int result = 0;
		try {
			result = session.insert("insertBoard",boardDTO);
		} catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}

	@Override
	public int totalBoard() {
		int total = session.selectOne("totalBoard");
		return total;
	}
	
	// 게시글 목록
	@Override
	public List<BoardDTO> boardList(BoardDTO boardDTO) {
		Map<String, Object> board = new HashMap<>();
		board.put("start", boardDTO.getStart());
		board.put("end", boardDTO.getEnd());
		board.put("board", null);
		session.selectList("boardList",board);   // 프로시저
		List<BoardDTO> boardList = (List<BoardDTO>) board.get("board");
		
		
	//	List<BoardDTO> boardList = session.selectList("board",boardDTO); 
		
		return boardList;
	}
	
	// 게시글 상세보기 + 조회수 증가
	@Override
	public BoardDTO detail(BoardDTO boardDTO1) {
		// 조회수를 올리고 조회하기
		session.update("boardHitUp",boardDTO1);		 					// 조회수 증가
		BoardDTO boardDTO = session.selectOne("BoardDetail",boardDTO1);	// 증가된 후 데이터 조회해서 리턴
		return boardDTO;
	}

}
