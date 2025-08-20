package com.oracle.coffee.dao.km;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.km.BoardDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
@RequiredArgsConstructor
public class BoardDaoImpl implements BoardDao {
	
	private final SqlSession session;

	@Override
	public void boardWrite(BoardDTO boardDTO) {
		try {
			session.insert("boardWrite" , boardDTO);
			System.out.println("BoardDaoImpl boardWrite boardDTO->"+boardDTO);
		} catch (Exception e) {
			System.out.println("BoardDaoImpl boardWrite Exception->"+e.getMessage());
		}
		
	}

	@Override
	public int boardTotal() {
		int boardTotal = 0;
		try {
			boardTotal = session.selectOne("boardTotal");
			System.out.println("BoardDaoImpl boardTotal boardTotal->"+boardTotal);
		} catch (Exception e) {
			System.out.println("BoardDaoImpl boardTotal Exception->"+e.getMessage());
		}
		return boardTotal;
	}

	@Override
	public List<BoardDTO> boardList(BoardDTO boardDTO) {
		List<BoardDTO> boardList = null;
		try {
			boardList = session.selectList("boardList" , boardDTO);
			System.out.println("BoardDaoImpl boardList boardList->"+boardList);
		} catch (Exception e) {
			System.out.println("BoardDaoImpl boardList Exception->"+e.getMessage());
		}
		return boardList;
	}
	
	@Override
	public void upReadCount(BoardDTO boardDTO1) {
		try {
			session.update("upReadCount" , boardDTO1);
			System.out.println("BoardDaoImpl upReadCount boardDTO1->"+boardDTO1);
		} catch (Exception e) {
			System.out.println("BoardDaoImpl upReadCount Exception->"+e.getMessage());
		}
		
	}

	@Override
	public BoardDTO boardView(BoardDTO boardDTO) {
		BoardDTO boardView = null;
		try {
			boardView = session.selectOne("boardView" , boardDTO);
			System.out.println("BoardDaoImpl boardView boardView->"+boardView);
		} catch (Exception e) {
			System.out.println("BoardDaoImpl boardView Exception->"+e.getMessage());
		}
		return boardView;
	}

	@Override
	public void boardUpdate(BoardDTO boardDTO) {
		try {
			session.update("boardUpdate" , boardDTO);
			System.out.println("BoardDaoImpl boardUpdate boardDTO->"+boardDTO);
		} catch (Exception e) {
			System.out.println("BoardDaoImpl boardUpdate Exception->"+e.getMessage());
		}
		
	}

	@Override
	public void boardDelete(BoardDTO boardDTO) {
		try {
			session.delete("boardDelete" , boardDTO);
			System.out.println("BoardDaoImpl boardDelete boardDTO->"+boardDTO);
		} catch (Exception e) {
			System.out.println("BoardDaoImpl boardDelete Exception->"+e.getMessage());
		}
		
	}

}
