package com.oracle.oBootHello.repository;

import java.util.List;

import com.oracle.oBootHello.dto.Member1;

public interface MemberLegacyRepository {
	Member1 save(Member1 member1);
	String  findAll();
}
