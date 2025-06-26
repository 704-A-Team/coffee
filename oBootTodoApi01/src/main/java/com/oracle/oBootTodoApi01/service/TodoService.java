package com.oracle.oBootTodoApi01.service;

import com.oracle.oBootTodoApi01.dto.PageRequestDTO;
import com.oracle.oBootTodoApi01.dto.PageResponseDTO;
import com.oracle.oBootTodoApi01.dto.TodoDTO;

public interface TodoService {
	Long 						register(TodoDTO todoDTO);
	int							todoTotal();
	TodoDTO 					get(Long tno);
	PageResponseDTO<TodoDTO> 	list(PageRequestDTO pageRequestDTO);
}
