package com.oracle.oBootBoard.dao;

import java.util.ArrayList;

import com.oracle.oBootBoard.dto.BDto;

public interface BDao {
	public ArrayList<BDto> boardList();
	public BDto contentView(String strId);
	public void modify(String bId, String bName, String bTitle, String bContent);
	public void bDelete(String bId);
	public void write(String bName, String bTitle, String bContent);
	public BDto reply_view(String bId);
	public void reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent);
}
