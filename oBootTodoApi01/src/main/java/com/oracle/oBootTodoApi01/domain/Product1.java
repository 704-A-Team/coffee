package com.oracle.oBootTodoApi01.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Product_1")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageList")
@SequenceGenerator(
						name = "seq",
						sequenceName = "DB_Seq",
						initialValue = 1,
						allocationSize = 1
					)
public class Product1 {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "seq"
					)
	private Long 	pno;
	private String 	pname;
	private int 	price;
	private String 	pdesc;
	private boolean delFlag; // 이미지 삭제 여부
	private String 	keyword;
	@ElementCollection
	@Builder.Default
	List<ProductImage1> imageList = new ArrayList<>();
}
