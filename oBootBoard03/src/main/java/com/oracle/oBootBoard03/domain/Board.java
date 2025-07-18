package com.oracle.oBootBoard03.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(
					name = "board_seq",
					sequenceName = "board_seq_gen",
					initialValue = 1,
					allocationSize = 1
	)
public class Board {
	@Id
	@GeneratedValue(
					strategy = GenerationType.SEQUENCE,
					generator = "board_seq"
	)
	private int 		  board_no;
	private String 		  title;
	private String 		  content;
	private int 		  emp_no;
	private int 		  read_count;
	private int 		  ref;
	private int 		  re_step;
	private int 		  re_lvl;
	@CreatedDate
	private LocalDateTime in_date;
	
	public void changeTitle(String title) {
		this.title = title;
	}
	public void changeContent(String content) {
		this.content = content;
	}
	public void changeEmp_no(int emp_no) {
		this.emp_no = emp_no;
	}
	public void changeIn_date(LocalDateTime in_date) {
		this.in_date = in_date;
	}
	
	

}
