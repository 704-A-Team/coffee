package com.oracle.coffee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.coffee.dto.ClientDto;
import com.oracle.coffee.dto.DeptDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {
	
	@GetMapping("/clientInForm")
	public String clientInForm() {
		System.out.println("clientInForm 시작");
		return "client/clientInForm";
	}

	@GetMapping("/clientList")
	public String clientList(ClientDto clientDto, Model model) {
		System.out.println("clientList 시작");
		
		return "client/clientList";
	}
	
	
}
