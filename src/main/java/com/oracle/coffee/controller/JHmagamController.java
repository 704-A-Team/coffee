package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.coffee.dto.InventoryDto;
import com.oracle.coffee.dto.MagamDto;
import com.oracle.coffee.service.JHMagamService;
import com.oracle.coffee.service.Paging;


@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/jh")
public class JHmagamController {
	
	private final JHMagamService jhMagamService;
	
	@GetMapping(value = "/magamList")
    public String inventoryPage(@RequestParam(value="page", defaultValue="1") int currentPage,
    		MagamDto magamDto , Model model) {
    	System.out.println("jh/JHmagam Start...");
    	
    	int totalMagamDto = jhMagamService.totalMagamDto();
    	//String currentPage = "1";
    	
    	Paging page = new Paging(totalMagamDto, String.valueOf(currentPage));
		// Parameter emp --> Page만 추가 Setting
		magamDto.setStart(page.getStart());   	// 시작시 1
		magamDto.setEnd(page.getEnd());        // 시작시 10
    	
    	List<MagamDto> magamList = jhMagamService.magamList(magamDto);
    	System.out.println("JHmagam magamList.size : "+magamList.size());
    	
    	model.addAttribute("totalMagamDto", totalMagamDto);
    	model.addAttribute("magamList", magamList);
    	model.addAttribute("page", page);
    	System.out.println("page.getTotalPage : "+page.getTotalPage());

        return "jh/magamList";
    }
	
	
	
	
	
	@GetMapping(value = "/magamDetail")
    public String monthMagamList(
    		@RequestParam("monthYrmo") String monthYrmo,
    		@RequestParam(value="page", defaultValue="1") int currentPage,
    		MagamDto magamDto , Model model) {
    	System.out.println("jh/monthMagamList Start...");
    	
    	// ✅ 조회 조건 주입
        magamDto.setMonth_yrmo(monthYrmo);
    	
    	int countMonMag = jhMagamService.countMonMagAll(magamDto);
    	//String currentPage = "1";
    	
    	Paging page = new Paging(countMonMag, String.valueOf(currentPage));
		// Parameter emp --> Page만 추가 Setting
		magamDto.setStart(page.getStart());   	// 시작시 1
		magamDto.setEnd(page.getEnd());        // 시작시 10
    	
    	List<MagamDto> monthMagamList = jhMagamService.monthMagamList(magamDto);
    	System.out.println("JHmagam monthMagamList.size : "+monthMagamList.size());
    	
    	model.addAttribute("countMonMagAll", countMonMag);
    	model.addAttribute("monthMagamList", monthMagamList);
    	model.addAttribute("page", page);
    	model.addAttribute("monthYrmo", monthYrmo);

        return "jh/magamDetail";
    }
	
	
	
}
