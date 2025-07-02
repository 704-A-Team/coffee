package com.oracle.oBootTodoApi01.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oracle.oBootTodoApi01.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("select p From Product p where p.pno = :pno")
	Optional<Product> selectOne(@Param("pno") Long pno);
	
	
	
	// @Query를 통해 INSERT, DELETE, UPDATE쿼리를 쓰게 될때 무조건 사용해야하는 어노테이션
	@Modifying
	@Query("update Product p set p.delFlag = :flag where p.pno = :pno") // 마스터 데이터는 삭제하지 않는다 (삭제처럼 보이는 업데이트 되기)
	void updateToDelete(@Param("pno") Long pno, @Param("flag") boolean flag);
	
}
