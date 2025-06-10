package com.oracle.oBootBoard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootBoard.command.BExecuteCommand;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class BController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BController.class);
	
	// Service DI
	private final BExecuteCommand bExecuteService;
	@Autowired
	public BController(BExecuteCommand bExecuteService) {
		this.bExecuteService = bExecuteService;
	}
	
	@RequestMapping("list")
	public String List(Model model) {
		LOGGER.info("list start");
		// 위에 연결했으니까 Service 연결 11:30 6/9
		
		// 넘겨줄때 bListCmd(model) 는 addAttribute로 안해도 되는이유
		// model은 객체라 call by reference 니까 주소값이 넘겨진다
		bExecuteService.bListCmd(model);
		model.addAttribute("count",50);
		return "list";
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		System.out.println("content_view start");
		
		model.addAttribute("request", request);
		bExecuteService.bContentCmd(model);
		
		return "content_view";
	}
	
	@RequestMapping(value = "/modify")
	public String modify(HttpServletRequest request, Model model) {
		LOGGER.info("modify start");
		model.addAttribute("request", request);
		bExecuteService.bModifyCmd(model);
		// redirect -> 같은 컨트롤러내에 list 메소드를 찾는다
		return "redirect:list";
	}
	
	
	
	
	@RequestMapping("/delete")
	public String delet(HttpServletRequest request, Model model) {
		LOGGER.info("delete start");
		
		model.addAttribute("request", request);
		bExecuteService.bDeleteCmd(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/write_view")
	public String write_view(Model model)  {
		LOGGER.info("write_view start");
		return "write_view";
	}
	
	@PostMapping("/write")
	public String write(HttpServletRequest request, Model model) {
		LOGGER.info("write start");
		
		model.addAttribute("request", request);
		bExecuteService.bWriteCmd(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply_view")
	public String replay_view(HttpServletRequest request, Model model) {
		System.out.println("reply_view start");
		
		model.addAttribute("request", request);
		bExecuteService.bReplyViewCmd(model);
		return "reply_view";
	}
	
	@PostMapping(value = "/reply")
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("reply");
		
		model.addAttribute("request", request);
		bExecuteService.bReplyCmd(model);
		
		return "redirect:list";
	}
	
	
}
