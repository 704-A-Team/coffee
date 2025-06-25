package com.oracle.oBootTodoApi01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.oBootTodoApi01.domain.Todo;
													// DTO, ID Type
public interface TodoRepository extends JpaRepository<Todo, Long>{

}
