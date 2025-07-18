package com.oracle.oBootBoard03.repository;

import org.springframework.stereotype.Repository;

import com.oracle.oBootBoard03.domain.Board;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {
	
	private final EntityManager em;

	@Override
	public void Boardwrite(Board board) {
		em.persist(board);
		
	}

}
