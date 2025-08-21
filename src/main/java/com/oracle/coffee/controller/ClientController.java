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
	
	//거래처 목록
	@GetMapping("/clientList")
	public String clientList(ClientDto clientDto, Model model) {
	    // 총 개수 (검색 조건 반영)
	    Long totalCountLong = clientService.totalClient(clientDto);
	    int totalCountInt   = totalCountLong.intValue();
	    // 페이징 관련
	    Paging page = new Paging(totalCountInt, clientDto.getCurrentPage());
	    clientDto.setStart(page.getStart());
	    clientDto.setEnd(page.getEnd());
	    // 거래처 목록 조회 (검색+페이징 반영)
	    List<ClientDto> clientDtoList = clientService.clientList(clientDto);
	    model.addAttribute("totalCount", totalCountInt);
	    model.addAttribute("clientDtoList", clientDtoList);
	    model.addAttribute("page", page);
	    // 검색 파라미터 유지용 
	    model.addAttribute("searchType",   clientDto.getSearchType());
	    model.addAttribute("searchKeyword",clientDto.getSearchKeyword());
	    model.addAttribute("status",       clientDto.getStatus());
	    
	    return "client/clientList";
	}

	//거래처 등록 폼
	@GetMapping("/clientInForm")
	public String clientInsertForm(Model model) {
	    List<EmpDto> empList = empRepository.findAllEmp();  
	    model.addAttribute("empList", empList);         
	    
	    return "client/clientInForm";                      
	}
	
	//거래처 등록
	@PostMapping("/saveClient")
	public String saveClient(ClientDto clientDto) {
		clientService.clientSave(clientDto);
		
		return "redirect:clientList";
	}
	
	//거래처 상세 
	@GetMapping("/clientDetail")
	public String clientDetail (ClientDto clientDto, Model model) {
		ClientDto clientDetail = clientService.getSingleClient(clientDto.getClient_code());
		model.addAttribute("clientDto", clientDetail);

		return "client/clientDetail";
	}
	
	//거래처 수정 폼 
	@GetMapping("/modifyForm")
	public String clientModify (ClientDto clientDto, Model model) {
		ClientDto clientModify = clientService.getSingleEmp(clientDto.getClient_code());
		model.addAttribute("clientDto", clientModify);
		List<EmpDto> empList = empRepository.findAllEmp();
	    model.addAttribute("empList", empList);
	    
		return "client/clientModifyForm";
	}
	
	//거래처 수정 
	@PostMapping("/clientUpdate")
	public String clientUpdate(ClientDto clientDto) {
		clientService.clientUpdate(clientDto);
		
		return "redirect:clientList";
	}
	

}
