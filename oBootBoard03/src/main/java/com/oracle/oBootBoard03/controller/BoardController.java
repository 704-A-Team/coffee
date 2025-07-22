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
		List<EmpDTO> empName = empService.findAllEmp();
		// EmpDTO로 받아온 emp_no, emp_name  -> 작성자를 드롭박스로 표현하려한다
		log.info("empName"+empName);
		model.addAttribute("empName",empName);
		return "board/write_view";
	}
	
	@PostMapping("/write")
	public String write(BoardDTO boardDTO) {
		log.info("board write boardDTO->"+boardDTO);
		boardService.Boardwrite(boardDTO);
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/list")
	public String BoardList(BoardDTO boardDTO, Model model) {
		int total = boardService.totalBoard();
		Paging page = new Paging(total, boardDTO.getCurrentPage());
		boardDTO.setStart(page.getStart());
		boardDTO.setEnd(page.getEnd());
		List<BoardDTO> boardList = boardService.boardList(boardDTO);
		model.addAttribute("boardList", boardList);
		return "list";
	}

}
