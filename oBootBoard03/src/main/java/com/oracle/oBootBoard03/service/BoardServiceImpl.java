package com.oracle.oBootBoard03.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.oracle.oBootBoard03.dao.BoardDao;
import com.oracle.oBootBoard03.domain.Board;
import com.oracle.oBootBoard03.dto.BoardDTO;
import com.oracle.oBootBoard03.repository.BoardRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {
	
	private final BoardDao boardDao;
	private final BoardRepository boardRepository;
	private final ModelMapper mapper;

	@Override
	public int totalBoard() {
		int total = boardDao.totalBoard();
		return total;
	}

	@Override
	public List<BoardDTO> boardList(BoardDTO boardDTO) {
		List<BoardDTO> boardList = boardDao.boardList(boardDTO);
		return boardList;
	}

	@Override // 게시글 내용 저장
	public void Boardwrite(BoardDTO boardDTO) {
		Board board = mapper.map(boardDTO, Board.class);
		log.info("mapper board->"+board);
	//	boardRepository.Boardwrite(board);
		boardDao.insertBoard(boardDTO);
		log.info("insertBoard boardDTO->"+boardDTO);
	}

	// 게시글 상세보기
	@Override
	public BoardDTO detail(BoardDTO boardDTO1) {
		BoardDTO boardDTO = boardDao.detail(boardDTO1);
		return boardDTO;
	}
	
	// 게시글 삭제하기
	@Override
	public void delete(int board_no) {
		boardDao.delete(board_no);
		
	}

}
