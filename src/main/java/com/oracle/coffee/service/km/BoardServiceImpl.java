package com.oracle.coffee.service.km;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.coffee.dao.km.BoardDao;
import com.oracle.coffee.dto.km.BoardDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardDao boardDao;

	@Override
	public void boardWrite(BoardDTO boardDTO) {
		System.out.println("BoardServiceImpl boardWrite boardDTO->"+boardDTO);
		boardDao.boardWrite(boardDTO);
		
	}

	@Override
	public int boardTotal() {
		System.out.println("BoardServiceImpl boardTotal Start");
		int boardTotal = boardDao.boardTotal();
		System.out.println("BoardServiceImpl boardTotal boardTotal->"+boardTotal);
		return boardTotal;
	}

	@Override
	public List<BoardDTO> boardList(BoardDTO boardDTO) {
		List<BoardDTO> boardList = boardDao.boardList(boardDTO);
		System.out.println("BoardServiceImpl boardList boardList->"+boardList);
		return boardList;
	}
	
	@Override
	public void upReadCount(BoardDTO boardDTO1) {
		boardDao.upReadCount(boardDTO1);
		System.out.println("BoardServiceImpl upReadCount");
		
	}

	@Override
	public BoardDTO boardView(BoardDTO boardDTO) {
		BoardDTO boardView = boardDao.boardView(boardDTO);
		System.out.println("BoardServiceImpl boardView boardView->"+boardView);
		return boardView;
	}

	@Override
	public void boardUpdate(BoardDTO boardDTO) {
		boardDao.boardUpdate(boardDTO);
		System.out.println("BoardServiceImpl boardUpdate boardDTO->"+boardDTO);
		
	}

	@Override
	public void boardDelete(BoardDTO boardDTO) {
		boardDao.boardDelete(boardDTO);
		System.out.println("BoardServiceImpl boardDelete boardDTO->"+boardDTO);
		
	}
	

}
