package com.oracle.oBootJpaApi01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "team5")
//1) 객체 nm : team_seq_gen5
//2) DB  nm : team_seq_generator5
//3) 초기 -> 1 , 할당 ->1
@SequenceGenerator( 
						name = "team_seq_gen5",
						sequenceName = "team_seq_generator5",
						initialValue = 1,
						allocationSize = 1
					)
public class Team {
	@Id
	@GeneratedValue(
						strategy = GenerationType.SEQUENCE,
						generator = "team_seq_gen5"
					)
	private Long 	teamId;
	
	@Column(name = "teamname", length = 50)
	private String 	name;

}
