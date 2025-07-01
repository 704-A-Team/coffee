package com.oracle.oBootTodoApi01.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootTodoApi01.dao.TodoDao;
import com.oracle.oBootTodoApi01.domain.Todo;
import com.oracle.oBootTodoApi01.dto.PageRequestDTO;
import com.oracle.oBootTodoApi01.dto.PageResponseDTO;
import com.oracle.oBootTodoApi01.dto.TodoDTO;
import com.oracle.oBootTodoApi01.repository.TodoRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
	
	// 자동주입 대상은 final
	private final TodoRepository todoRepository;
	private final ModelMapper modelMapper;
	private final TodoDao todoDao;
	
	// Controller에서 파라미터 받아올때는 DTO로 받아온다
	// CRUD 만 repository에서 domain(엔터티)로 하고
	@Override
	public Long register(TodoDTO todoDTO) {
		log.info("register start todoDTO->" + todoDTO);
		// 		DTO --(변경)-> Entity		두 개 layout이 동일해야 한다
		Todo todo = modelMapper.map(todoDTO, Todo.class);
		
		Todo saveTodo = todoRepository.save(todo);
		
		return saveTodo.getTno();
	}

	@Override
	public int todoTotal() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public TodoDTO get(Long tno) {
		//                            레포지토리는 반드시 Entity로 반환한다
		Optional<Todo> maybeTodo = todoRepository.findById(tno);
		Todo todo = maybeTodo.orElseThrow();  // 값이 있으면 todo에 넣어주고 NULL이면 throw 해줘
		
		TodoDTO dto = modelMapper.map(todo, TodoDTO.class);  // 엔터티를 dto로 복사해줘
		return dto;
	}

	@Override
	public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
		// JPA로 페이징 작업 힘듦 -> 마이바티스 사용
		
			// rownum BETWEEN start And end
		List<TodoDTO> dtoList = todoDao.listTodo(pageRequestDTO);
		System.out.println("TodoServiceImpl list dtoList->"+dtoList);
		
			//총개수 Select Count(*)
		int totalCount = todoDao.totalTodo();
		
		PageResponseDTO<TodoDTO> responseDTO = 
					PageResponseDTO.<TodoDTO>withAll()
									.dtoList(dtoList)
									.pageRequestDTO(pageRequestDTO)
									.totalCount(totalCount)
									.build()
									;
		return responseDTO;
	}

	@Override
	public void modify(TodoDTO todoDTO) {
		Optional<Todo> maybeTodo = todoRepository.findById(todoDTO.getTno());
		Todo todo = maybeTodo.orElseThrow();
		todo.changeTitle(todoDTO.getTitle());
		todo.changeWriter(todoDTO.getWriter());
		todo.changeDueDate(todoDTO.getDue_date());
		todo.changeComplete(todoDTO.isComplete()); // boolean 일때는 is로 제공
		System.out.println("TodoServiceImpl modify todo : "+todo);
		
		todoRepository.save(todo); // entity 로 넘겨줘야 하니까 todo로 하였다
	}

	@Override
	public void remove(Long tno) {
		todoRepository.deleteById(tno);
		
	}

}
