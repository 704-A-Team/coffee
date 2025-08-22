package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.coffee.dto.AccountDto;
import com.oracle.coffee.dto.km.BoardDTO;
import com.oracle.coffee.service.km.BoardService;
import com.oracle.coffee.service.km.Paging;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/boardWriteForm")
	public String boardWriteForm() {
		
		return "board/boardWrite";
	}
	
	// 게시글 등록
	@PostMapping("/boardWrite")
	public String boardWrite(@AuthenticationPrincipal AccountDto emp , BoardDTO boardDTO) {
		System.out.println("BoardController boardWrite boardDTO->"+boardDTO);
		boardDTO.setBoard_reg_code(emp.getEmp_code());
		boardService.boardWrite(boardDTO);	
		
		return "redirect:/board/boardList";
	}
	
	// 게시글 목록
	@GetMapping("/boardList")
	public String boardList(BoardDTO boardDTO , Model model) {
		
		int total = boardService.boardTotal();
		Paging page = new Paging(total, boardDTO.getCurrentPage());
		boardDTO.setStart(page.getStart());
		boardDTO.setEnd(page.getEnd());
		
		List<BoardDTO> boardList = boardService.boardList(boardDTO);
		model.addAttribute("boardList" , boardList);
		model.addAttribute("page" , page);
		return "board/boardList";
	}
	
	// 게시글 상세
	@GetMapping("/boardView")
	public String boardView(BoardDTO boardDTO1 , Model model) {
		System.out.println("BoardController boardView start...");
		
		// 게시글 조회수
		boardService.upReadCount(boardDTO1);
		System.out.println("BoardController boardDTO1.board_code : " + boardDTO1.getBoard_code());
		
		// 게시글 상세보기
		BoardDTO boardDTO = boardService.boardView(boardDTO1);
		model.addAttribute("boardDTO" , boardDTO);
		return "board/boardView";
	}
	
	// 게시글 수정 폼
	@GetMapping("/boardEditForm")
	public String boardEditForm(BoardDTO boardDTO1 , Model model) {
		
		BoardDTO boardDTO = boardService.boardView(boardDTO1);
		model.addAttribute("boardDTO" , boardDTO);
		return "board/boardEditForm";
	}
	
	// 게시글 수정 저장
	@PostMapping("/boardUpdate")
	public String boardUpdate(BoardDTO boardDTO , Model model) {
		
		System.out.println("BoardController boardUpdate boardDTO->"+boardDTO);
		boardService.boardUpdate(boardDTO);
		
		BoardDTO boardDTO1 = boardService.boardView(boardDTO);
		model.addAttribute("boardDTO" , boardDTO1);
		return "board/boardView";
	}
	
	// 게시글 삭제
	@GetMapping("/boardDelete")
	public String boardDelete(BoardDTO boardDTO) {
		
		System.out.println("BoardController boardDelete boardDTO->"+boardDTO);
		boardService.boardDelete(boardDTO);
		
		return "redirect:/board/boardList";
	}
	
	
	
	
	
	

}
