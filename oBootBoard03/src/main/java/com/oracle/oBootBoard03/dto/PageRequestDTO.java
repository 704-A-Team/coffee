package com.oracle.oBootBoard03.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// 상속관계에서도 부모 Class 필드를 자식 Class Builder에서 설정이 가능하도록 하기 위해서 SuperBuilder 사용
// 부모,자식 둘 다 @SuperBuilder 지정
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
	@Builder.Default
	private int page = 1;
	
	@Builder.Default
	private int size = 10;
	// 기본값을 보존해주는 장치!
	private int start;
	private int end;

}
