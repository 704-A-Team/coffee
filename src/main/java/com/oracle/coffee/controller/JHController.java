package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.coffee.dto.InventoryDto;
import com.oracle.coffee.service.JHservice;
import com.oracle.coffee.service.Paging;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/jh")
public class JHController {
	
	private final JHservice jHservice;

    @RequestMapping(value = "jhInventoryInForm")
    public String inventoryPage(InventoryDto inventoryDto , Model model) {
    	System.out.println("jh/jhInventoryInForm Start...");
    	
    	int totalInventoryDto = jHservice.totalInventoryDto();
    	String currentPage = "1";
    	
    	Paging page = new Paging(totalInventoryDto, currentPage);
		// Parameter emp --> Page만 추가 Setting
		inventoryDto.setStart(page.getStart());   // 시작시 1
		inventoryDto.setEnd(page.getEnd());       // 시작시 10 
    	
    	List<InventoryDto> inventoryList = jHservice.inventoryList(inventoryDto);
    	System.out.println("JHController InventoryList.size : "+inventoryList.size());
    	
    	model.addAttribute("totalInventoryDto", totalInventoryDto);
    	model.addAttribute("inventoryList", inventoryList);
    	model.addAttribute("page", page);
    	
        return "jh/inventoryList";
    }
}
