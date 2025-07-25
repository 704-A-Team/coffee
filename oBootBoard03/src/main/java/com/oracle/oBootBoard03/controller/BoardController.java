package com.oracle.oBootBoard03.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootBoard03.dto.BoardDTO;
import com.oracle.oBootBoard03.dto.EmpDTO;
import com.oracle.oBootBoard03.service.BoardService;
import com.oracle.oBootBoard03.service.EmpService;
import com.oracle.oBootBoard03.service.Paging;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/board")
public class BoardController {
	
	private final EmpService empService;
	private final BoardService boardService;
	
	@GetMapping(value = "/write_view")
	public String writeVeiw(Model model) {
		// EmpDTO로 받아온 emp_no, emp_name  -> 작성자를 드롭박스로 표현하려한다
		List<EmpDTO> empName = empService.findAllEmp();
		log.info("empName"+empName);
		model.addAttribute("empName",empName);
		return "board/write_view";
	}
	
	// 게시글 등록
	@PostMapping("/write")
	public String write(BoardDTO boardDTO) {
		log.info("board write boardDTO->"+boardDTO);
		boardService.Boardwrite(boardDTO);
		
		return "redirect:/board/list";
	}
	
	// 게시글 목록
	@GetMapping("/list")
	public String BoardList(BoardDTO boardDTO, Model model) {
		int total = boardService.totalBoard();
		
		Paging page = new Paging(total, boardDTO.getCurrentPage());
		boardDTO.setStart(page.getStart());
		boardDTO.setEnd(page.getEnd());
		
		List<BoardDTO> boardList = boardService.boardList(boardDTO);
		model.addAttribute("boardList", boardList);
		model.addAttribute("page",page);
		return "board/list";
	}
	
	// 게시글 상세조회 + 조회수 올리기
	@GetMapping("/detail")
	public String detail(BoardDTO boardDTO1, Model model) {
		// 게시글 상세보기
		System.out.println("board_no :" +boardDTO1.getBoard_no());
		BoardDTO boardDTO = boardService.detail(boardDTO1);
		model.addAttribute("board",boardDTO);
		return "board/detail";
	}
	
	// 답글 : 모든 사용자(본인포함)가 달 수 있다
	
	
	// 수정, 삭제 : 게시글 작성자만 가능하다
	@PostMapping("/delete")
	public String delete(BoardDTO boardDTO) {
		System.out.println("board_no"+boardDTO.getBoard_no());
		boardService.delete(boardDTO.getBoard_no());
		
		return "redirect:/board/list";
	}

}
