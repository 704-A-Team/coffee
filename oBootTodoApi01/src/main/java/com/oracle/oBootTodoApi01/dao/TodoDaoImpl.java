package com.oracle.oBootTodoApi01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootTodoApi01.dto.PageRequestDTO;
import com.oracle.oBootTodoApi01.dto.TodoDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TodoDaoImpl implements TodoDao {
	
	private final SqlSession session;

	@Override
	public int totalTodo() {
		int totTodoCount = 0;
		System.out.println("TodoDaoImpl totalTodo() start");
		try {
			totTodoCount = session.selectOne("com.oracle.oBootTodoApi01.TodoMapper.todoTotal");
			System.out.println("TodoDaoImpl totalTodo totTodoCount-> "+totTodoCount);
		} catch (Exception e) {
			System.out.println("TodoDaoImpl totalTodo Exception-> "+e.getMessage());
		}
		return totTodoCount;
	}

	@Override
	public List<TodoDTO> listTodo(PageRequestDTO pageRequestDTO) {
		int end = 0;
		List<TodoDTO> todoList = null;
		
		// 파라메타를 TodoDTO로 보내기
		// Start,End row 설정
		TodoDTO todoDTO = new TodoDTO();
		todoDTO.setStart(pageRequestDTO.getStart());
		todoDTO.setEnd(pageRequestDTO.getEnd());
		System.out.println("TodoDaoImpl listTodo() todoDTO->"+todoDTO);
		try {
			todoList = session.selectList("tkTodoListAll",todoDTO);
			System.out.println("TodoDaoImpl listTodo() todoList.size()-> "+todoList.size());
		} catch (Exception e) {
			System.out.println("TodoDaoImpl listTodo Exception-> "+e.getMessage());
		}
		return todoList;
	}

}
