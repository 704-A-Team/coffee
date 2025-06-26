package com.oracle.oBootTodoApi01.dao;

import java.util.List;

import com.oracle.oBootTodoApi01.dto.PageRequestDTO;
import com.oracle.oBootTodoApi01.dto.TodoDTO;

public interface TodoDao {
	int 			totalTodo();
	List<TodoDTO>	listTodo(PageRequestDTO pageRequestDTO);
}
