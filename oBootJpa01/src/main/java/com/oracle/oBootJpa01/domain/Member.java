package com.oracle.oBootJpa01.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 4:20 6/10
@Entity
@Table(name = "Member1")  // 물리적       테이블 이름  ->  Member1
	// 6/11 11:11
@Getter
@Setter
@ToString
public class Member {     // 객체        객체 이름   ->  Member
	@Id
	private Long	id;
	private String  name;
	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	
//	@Override
//		public String toString() {
//			String returnstr = "[id : " + this.id + " , name : " + this.name +" ]";
//			return returnstr;
//		}
}
