package com.oracle.oBootTodoApi01.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootTodoApi01.dto.PageRequestDTO;
import com.oracle.oBootTodoApi01.dto.PageResponseDTO;
import com.oracle.oBootTodoApi01.dto.TodoDTO;
import com.oracle.oBootTodoApi01.service.Paging;
import com.oracle.oBootTodoApi01.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/todo")
public class TodoController {
	
	private final TodoService todoService;
	
	@PostMapping("/register")
	public Map<String, Long> register(@RequestBody TodoDTO todoDTO){
		log.info("register todoDTO-> "+todoDTO);
		
		Long tno = todoService.register(todoDTO);
	
		return Map.of("TNO",tno);
	}
	// 식별자를 갖고 들어오면 uri
	@GetMapping("/{tno}")
	public TodoDTO get(@PathVariable(name = "tno") Long tno) {
		System.out.println("TodoController tno->"+tno);
		return todoService.get(tno);
	}
	
	@GetMapping("/list")
	public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
		System.out.println("TodoController list start");
		System.out.println("TodoController list pageRequestDTO-> "+pageRequestDTO);
		
		int totalCount = todoService.todoTotal();
		System.out.println("TodoController list totalCount-> "+totalCount);
		
		// Paging 작업								현재 페이지
		Paging page = new Paging(totalCount, pageRequestDTO.getPage());
		// Parameter emp --> Page만 추가 Setting
		pageRequestDTO.setStart(page.getStart());
		pageRequestDTO.setEnd(page.getEnd());
		
		System.out.println("TodoController list page after pageRequestDTO-> "+pageRequestDTO);
		log.info(pageRequestDTO);
		
		return todoService.list(pageRequestDTO);
	}
	
	@PutMapping("/modify/{tno}")
	public Map<String, String> modify(@PathVariable(name = "tno") Long tno
									, @RequestBody TodoDTO todoDTO) {
		todoDTO.setTno(tno);
		log.info("Modify : " + todoDTO);
		
		todoService.modify(todoDTO);
		
		return Map.of("RESULT","SUCCESS");
	}
	
	@DeleteMapping("/remove/{tno}")
	public Map<String, String> remove(@PathVariable(name = "tno") Long tno) {
		
		log.info("Remove : "+tno);
		
		todoService.remove(tno);
		
		return Map.of("RESULT","SUCCESS");
	}

}
