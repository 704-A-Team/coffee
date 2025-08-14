package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping(value = "/inventoryList")
    public String inventoryPage(
    		@RequestParam(value="page", defaultValue="1") int currentPage,
    		@RequestParam(value="isClosed", defaultValue="false") boolean isClosed,
    		InventoryDto inventoryDto,
    		Model model
    		) {
    	System.out.println("jh/JHController inventoryPage Start...");
    	log.info(">>> isClosed received = {}", isClosed);
    	
    	int totalInventory = jHservice.totalInventory();
    	//String currentPage = "1";
    	
    	Paging page = new Paging(totalInventory, String.valueOf(currentPage));
		// Parameter emp --> Page만 추가 Setting
		inventoryDto.setStart(page.getStart());   // 시작시 1
		inventoryDto.setEnd(page.getEnd());       // 시작시 10 
    	
    	List<InventoryDto> inventoryList = jHservice.inventoryList(inventoryDto);
    	System.out.println("JHController InventoryList.size : "+inventoryList.size());
    	
    	model.addAttribute("totalInventory", totalInventory);
    	model.addAttribute("inventoryList", inventoryList);
    	model.addAttribute("page", page);
    	model.addAttribute("isClosed", isClosed);
    	
        return "jh/inventoryList";
    }
    
    @GetMapping(value = "/mfgRequest")
    public String mfgRequestpage (InventoryDto inventoryDto, Model model) {
    	System.out.println("jh/JHController mfgRequestpage start...");
    	
    	List<InventoryDto> mfgReqList = jHservice.mfgReqList(inventoryDto);
    	System.out.println("JHController mfgReqList.size : "+mfgReqList.size());
    	
    	model.addAttribute("mfgReqList", mfgReqList);
    	
    	return "jh/mfgRequest";
    }
}