package com.oracle.oBootBoard.command;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dto.BDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BExecuteCommand {
	private final BDao jdbcDao;
	@Autowired
	public BExecuteCommand (BDao jdbcDao)  {
		this.jdbcDao = jdbcDao;
	}
	
	public void bListCmd(Model model)  {
		ArrayList<BDto> boardDtoList = jdbcDao.boardList();
		System.out.println("BListCommand boardDtoList.size()--> " + boardDtoList.size());
		model.addAttribute("boardList", boardDtoList);
	}

	public void bContentCmd(Model model) {
		// ----- Chapter 1
		// 1. model Map선언
		Map<String, Object> map = model.asMap(); // 12:30 6/9
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		// 2. parameter ->  bId
		String bId = request.getParameter("bId");
		// 12: 37
		System.out.println("bId: " + bId);
		BDto board = jdbcDao.contentView(bId);
		model.addAttribute("mvc_board", board);
	}

	public void bModifyCmd(Model model) {
		// ----- Chapter 1
		// 1. model Map선언
		Map<String, Object> map1 = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map1.get("request");
		
		// 2. parameter ->  bId, bName , bTitle , bContent
		String bId 		= request.getParameter("bId");
		String bName 	= request.getParameter("bName");
		String bTitle 	= request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		// jdbcDao.modify(bId, bName, bTitle, bContent);
		jdbcDao.modify(bId, bName, bTitle, bContent);
		//     model.addAttribute("mvc_board", board);  --> redirect:는 새로운 요청이니까  새로운 HTTP 요청이라 model은 소멸  즉, model.addAttribute()는 필요없다
	}

	public void bWriteCmd(Model model) {
//		  1) model이용 , map 선언
//		  2) request 이용 ->  bName  ,bTitle  , bContent  추출
//		  3) dao  instance 선언
//		  4) write method 이용하여 저장(bName, bTitle, bContent)
//         bid, bGroup,,bHit,  bStep, bIndent, bDate -> mvc_board_seq,mvc_board_seq, 0 , 0 , 0, 	sysdate
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		jdbcDao.write(bName, bTitle, bContent);
		
	}

	// bId --> 하나만 삭제    homeWork02
	public void bDeleteCmd(Model model) {
		//	 1)  model이용 , map 선언
	    //	 2) request 이용 ->  bId 추출
	    //	 3) dao  instance 선언
	    //	 4) delete method 이용하여 삭제
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bId = request.getParameter("bId");
		jdbcDao.bDelete(bId);
		
	}
}
