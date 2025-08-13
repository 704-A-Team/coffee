package com.oracle.coffee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/MyPage")
public class MyPageController {

	 @GetMapping("/MyPage")       
	 public String myPage(Model model) {
	  
	 return "MyPage/MyPage";  	    
	  }
	  
}
