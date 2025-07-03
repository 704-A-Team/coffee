package com.oracle.oBootTodoApi01.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage1 {
	// @Embeddable -> 식별자 없다
	// Product(포함된 엔터티)에 종속되어 있다 따라서
	// 생명주기가 Product 엔터티에 의존
	// 장점 :
	// 간단한 구조 : 복잡한 연관관계 없이 컬렉션 관리 가능
	// 자동 영속성 전이 : Product가 저장/삭제 되면 ProductImage도 함께 저장/삭제됨
	// @Embeddable에는 @Entity가 없다 !
	// 데이터베이스에서 테이블이 만들어질 때 부모(엔터티)의 PK가 반드시 함께 저장된다
	private String fileName;
	private int	   ord;
	
	public void setOrd(int ord) {
		this.ord = ord;
	}
}
