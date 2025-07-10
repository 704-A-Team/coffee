package com.oracle.oBootBoard03.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Embeddable
@Builder
@Getter
@ToString
@AllArgsConstructor  // 필수 ❌, 하지만 JPA나 테스트에서 생성자 주입할 때 유용
@NoArgsConstructor	 // 필수 ❌, 하지만 JPA나 JSON 역직렬화 등에서 필요함
public class EmpImage {
	//독립적인 식별자(@Id)가 없음
	//Product 엔티티에 완전히 종속됨
	//생명주기가 Product 엔티티에 의존함
	//@Embeddable 장점 
	//간단한 구조: 복잡한 연관관계 없이 컬렉션 관리 가능
	//자동 영속성 전이: Product가 저장/삭제되면 ProductImage도 함께 저장/삭제됨
	private int order_num;
	private String filename;
	
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}

}
