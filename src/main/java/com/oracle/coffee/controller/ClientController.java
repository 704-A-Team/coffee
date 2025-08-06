package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.coffee.dto.ClientDto;
import com.oracle.coffee.dto.EmpDto;
import com.oracle.coffee.repository.EmpRepository;
import com.oracle.coffee.service.ClientService;
import com.oracle.coffee.service.Paging;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {
	
	private final ClientService clientService;
	private final EmpRepository empRepository;
	
	@GetMapping("/clientList")
	public String clientList(ClientDto clientDto, Model model) {
		
		Long totalCountLong = clientService.totalClient();
		int totalCountInt = totalCountLong.intValue();
		Paging page = new Paging(totalCountInt, clientDto.getCurrentPage());

		clientDto.setStart(page.getStart());   
		clientDto.setEnd(page.getEnd());      
		
		List<ClientDto> clientDtoList = clientService.clientList(clientDto);

		model.addAttribute("totalCount", totalCountInt);
		model.addAttribute("clientDtoList" , clientDtoList);
		model.addAttribute("page", page);

		return "client/clientList";
	}

	
	@GetMapping("/clientInForm")
	public String clientInsertForm(Model model) {
	    List<EmpDto> empList = empRepository.findAllEmp();  // 사원 목록 가져옴
	    model.addAttribute("empList", empList);             // JSP에서 empList 사용 가능
	    return "client/clientInForm";                       // 등록 화면으로 이동
	}
	
	
	@PostMapping("/saveClient")
	public String saveClient(ClientDto clientDto) {
		clientService.clientSave(clientDto);
		
		return "redirect:clientList";
	}
	

	
	@GetMapping("/clientDetail")
	public String clientDetail (ClientDto clientDto, Model model) {
		ClientDto clientDetail = clientService.getSingleClient(clientDto.getClient_code());
		model.addAttribute("clientDto", clientDetail);

		return "client/clientDetail";
	}
	
	@GetMapping("/modifyForm")
	public String clientModify (ClientDto clientDto, Model model) {
		ClientDto clientModify = clientService.getSingleEmp(clientDto.getClient_code());
		model.addAttribute("clientDto", clientModify);
		
		List<EmpDto> empList = empRepository.findAllEmp(); // 필요 시 정렬, 필터 추가
	    model.addAttribute("empList", empList);
		return "client/clientModifyForm";
	}
	
	@PostMapping("/clientUpdate")
	public String clientUpdate(ClientDto clientDto) {
		clientService.clientUpdate(clientDto);
		
		return "redirect:clientList";
	}
	

}
