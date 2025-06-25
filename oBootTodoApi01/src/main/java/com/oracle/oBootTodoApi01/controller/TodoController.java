package com.oracle.oBootTodoApi01.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootTodoApi01.dto.TodoDTO;
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
	
	@GetMapping("/{tno}")
	public TodoDTO get(@PathVariable(name = "tno") Long tno) {
		System.out.println("TodoController tno->"+tno);
		return todoService.get(tno);
	}

}
