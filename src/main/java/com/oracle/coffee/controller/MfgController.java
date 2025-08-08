package com.oracle.coffee.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;
import com.oracle.coffee.dto.km.ProductWanDTO;
import com.oracle.coffee.service.km.MfgService;
import com.oracle.coffee.service.km.Paging;
import com.oracle.coffee.service.km.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/km")
public class MfgController {
	
	private final MfgService mfgService;
	private final ProductService productService;
	
	// 생산신청 폼
	@GetMapping("/mfgInForm")
	public String mfgInForm(Model model) {
		
		List<ProductWanDTO> mfgWanList = productService.mfgWanList();
		log.info("mfgWanList->"+mfgWanList);
		// 마감된 최근일을 가져온다
		String magamNext = mfgService.magamNext();
		
		model.addAttribute("magamNext" , magamNext);
		model.addAttribute("mfgWanList" , mfgWanList);
		return "mfg/mfgInForm";
	}
	
	// 생산신청 저장
	@PostMapping("/mfgRegister")
	public String mfgRegister(@RequestParam("mfgDetailListJson") String mfgRegister) {
		log.info("mfgRegister->"+mfgRegister);
		
		// 로그인한 사원 번호 저장
		MfgDTO mfgDTO = new MfgDTO();
		mfgDTO.setMfg_reg_code(2004);
		mfgDTO.setMfg_reg_date(LocalDateTime.now());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());  // 이게 LocalDateTime 처리 가능하게 함
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			List<MfgDetailDTO> mfgDetailDTO = mapper.readValue(mfgRegister, new TypeReference<List<MfgDetailDTO>>(){});
			mfgService.mfgRegister(mfgDTO , mfgDetailDTO);
			log.info("mfgDetailDTO->"+mfgDetailDTO);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return "redirect:/km/mfgList";
	}
	
	@GetMapping("/mfgList")
	public String mfgList(MfgDTO mfgDTO , Model model) {
		
		int total = mfgService.mfgTotal();
		Paging page = new Paging(total, mfgDTO.getCurrentPage());
		mfgDTO.setStart(page.getStart());
		mfgDTO.setEnd(page.getEnd());
		
		List<MfgDTO> mfgList = mfgService.mfgList(mfgDTO);
		log.info("mfgList->"+mfgList);
		
		model.addAttribute("mfgList" , mfgList);
		return "mfg/mfgList";
	}
	
	
	
	
}
