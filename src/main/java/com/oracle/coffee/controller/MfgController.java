package com.oracle.coffee.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oracle.coffee.dto.AccountDto;
import com.oracle.coffee.dto.km.CheckApproveResultDTO;
import com.oracle.coffee.dto.km.MfgDTO;
import com.oracle.coffee.dto.km.MfgDetailDTO;
import com.oracle.coffee.dto.km.MfgRpDTO;
import com.oracle.coffee.dto.km.MfgRpDetailDTO;
import com.oracle.coffee.dto.km.MfgRpSaveDTO;
import com.oracle.coffee.dto.km.ProductWanDTO;
import com.oracle.coffee.dto.km.RpDetailDTO;
import com.oracle.coffee.dto.km.WonCodeLackDTO;
import com.oracle.coffee.service.km.MfgService;
import com.oracle.coffee.service.km.Paging;
import com.oracle.coffee.service.km.ProductService;

import jakarta.validation.Valid;
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
	public String mfgRegister(@RequestParam("mfgDetailListJson") String mfgRegister , @AuthenticationPrincipal AccountDto emp) {
		log.info("mfgRegister->"+mfgRegister);
		
		// 로그인한 사원 번호 저장
		MfgDTO mfgDTO = new MfgDTO();
		mfgDTO.setMfg_reg_code(emp.getEmp_code());
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
	
	// 생산 신청 리스트
	@GetMapping("/mfgList")
	public String mfgList(MfgDTO mfgDTO , Model model) {
		
		int total = mfgService.mfgTotal();
		System.out.println("MfgController mfgList total->"+total);
		Paging page = new Paging(total, mfgDTO.getCurrentPage());
		mfgDTO.setStart(page.getStart());
		mfgDTO.setEnd(page.getEnd());
		System.out.println("MfgController mfgList page->"+page);
		List<MfgDTO> mfgList = mfgService.mfgList(mfgDTO);
		log.info("mfgList->"+mfgList);
		
		model.addAttribute("page" , page);
		model.addAttribute("mfgList" , mfgList);
		return "mfg/mfgList";
	}
	
	// 생상 신청 상세보기
	@GetMapping("/mfgDetail")
	public String mfgDetail(MfgDetailDTO mfgDetailDTO , Model model) {
		log.info("mfgDetail mfg_code->"+mfgDetailDTO.getMfg_code());
		
		List<MfgDetailDTO> mfgDetailList = mfgService.mfgDetail(mfgDetailDTO);
		log.info("mfgDetailList->"+mfgDetailList);
		
		model.addAttribute("mfgDetailList" , mfgDetailList);
		return "mfg/mfgDetail";
	}
	
	// 생산 신청 수정폼(직원)
	@GetMapping("/mfgUpdateForm")
	public String mfgUpdateForm(MfgDetailDTO mfgDetailDTO , Model model , RedirectAttributes redirectAttributes) throws JsonProcessingException {
		log.info("mfgUpdateForm mfg_code->"+mfgDetailDTO.getMfg_code());
		
		// 마감된 최근일을 가져온다
		String magamNext = mfgService.magamNext();
			
	    List<MfgDetailDTO> mfgDetailUpdate = null;
		
	    List<MfgDetailDTO> mfgStatus = mfgService.mfgStatus(mfgDetailDTO);
		log.info("mfgStatus->"+mfgStatus);
		// 요청 상태가 하나라도 있는지 확인
	    boolean hasRequestStatus = mfgStatus.stream()
	                                       .anyMatch(detail -> detail.getMfg_status() == 1);
	    
	    if (hasRequestStatus) {

	    	mfgDetailUpdate = mfgService.mfgUpdateForm(mfgDetailDTO);
	        ObjectMapper mapper = new ObjectMapper();
	        // Java 8 날짜/시간 지원 모듈 등록
	        mapper.registerModule(new JavaTimeModule());
	        // 필요하면 날짜 직렬화 관련 옵션도 설정 가능
	        String json = mapper.writeValueAsString(mfgDetailUpdate);
	        
	        // 요청 상태인 생산 신청 목록 가져오기
			List<ProductWanDTO> mfgWanList = productService.mfgWanList();
			log.info("mfgWanList->"+mfgWanList);
			
			model.addAttribute("mfgDetailUpdateJson", json);
	        model.addAttribute("magamNext" , magamNext); //  (add) 마감된 최근일을 가져온다
	        model.addAttribute("mfgWanList" , mfgWanList); 
	        model.addAttribute("mfgDetailDTO" , mfgDetailDTO); // 제품 코드 필요해서 넣었다
	        return "mfg/mfgUpdateForm";
	    } else {
	        redirectAttributes.addFlashAttribute("modalMessage", "요청 상태가 아닌 제품만 있어 수정할 수 없습니다.");
	        redirectAttributes.addAttribute("mfg_code", mfgDetailDTO.getMfg_code());
	        return "redirect:/km/mfgDetail";
	    }
	}
	
	// 생산 신청 수정 저장(직원)
	@PostMapping("/mfgUpdate")
	public String mfgUpdate(
			@RequestParam("mfg_code") int mfg_code
		  , @RequestParam("mfgDetailListJson") String mfgDetailListJson
		  , RedirectAttributes redirectAttributes) {
		log.info("받은 mfg_code->"+mfg_code);
		log.info("받은json->"+mfgDetailListJson);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		try {
			List<MfgDetailDTO> updateMfgList = mapper.readValue(mfgDetailListJson, new TypeReference<List<MfgDetailDTO>>() {});
			
			// 삭제 ( : 기존 - (유지 + 추가) )
			List<MfgDetailDTO> existingList = mfgService.existingList(mfg_code);
			
			for(MfgDetailDTO existingDTO : existingList) {
				boolean stillExists = updateMfgList.stream()
												   .anyMatch(update -> update.getProduct_code() == existingDTO.getProduct_code());
				                                   // 요소 중 조건을 만족하는 게 하나라도 있으면 true를 반환
				if(!stillExists) {
					mfgService.mfgDelete(existingDTO);
				}
			}
			
			// 추가 / 수정
			for(MfgDetailDTO updateMfg : updateMfgList) {
				// null 체크를 첫번째로 하여야 한다 안하면 NullPointerException
				// getMfg_code()가 Integer형일 경우 null이 먼저 체크
				// 만약 int 타입이라면 null 체크는 필요 없고 그냥 0 비교만 하면 된다
				if(updateMfg.getMfg_code() == 0) {
					// 추가 일 때
					updateMfg.setMfg_code(mfg_code);
					updateMfg.setMfg_status(1);
					mfgService.mfgInsert(updateMfg);
				} else {
					// 기존 변경
					mfgService.mfgUpdate(updateMfg);
				}
			}
			log.info("완료된 updateMfgList->"+updateMfgList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		redirectAttributes.addAttribute("mfg_code", mfg_code);
		return "redirect:/km/mfgDetail";
	}
	
	// 생산 신청 승인 폼(생산본부장)
	@GetMapping("/mfgApproveDetail")
	public String mfgApproveDetail(MfgDetailDTO mfgDetailDTO , Model model) {
		log.info("mfgDetail mfg_code->"+mfgDetailDTO.getMfg_code());
		
		List<MfgDetailDTO> mfgDetailList = mfgService.mfgDetail(mfgDetailDTO);
		log.info("mfgDetailList->"+mfgDetailList);
		
		String magamNext = mfgService.magamNext();
		
		model.addAttribute("mfgDetailList" , mfgDetailList);
		model.addAttribute("magamNext" , magamNext);
		return "mfg/mfgApproveDetail";
	}
	
	// 생산 신청 승인 수정 폼
	/*
	 * @GetMapping("/mfgApproveForm") public String mfgApproveForm(MfgDetailDTO
	 * mfgDetailDTO , Model model) {
	 * log.info("mfgDetailDTO mfg_code->"+mfgDetailDTO.getMfg_code());
	 * 
	 * List<MfgDetailDTO> approveMfg = mfgService.mfgApproveForm(mfgDetailDTO);
	 * model.addAttribute("approveMfg" , approveMfg); return "mfg/mfgApproveForm"; }
	 */
	
	// 생산 승인 버튼 클릭 시 가용재고에서 원재료 충분한지 확인
	@PostMapping("/checkApprove")
	@ResponseBody
	public Map<String, Object> checkApprove(@RequestBody MfgDetailDTO mfgDetailDTO) {
 		Map<String, Object> resultMap = new HashMap<>();
		System.out.println("MfgController checkApprove mfgDetailDTO->"+mfgDetailDTO);
		log.info(mfgDetailDTO);
		// 선행작업 mfgDetailDTO -> map으로 전환 
		mfgService.checkApprove(mfgDetailDTO);
		
		int result_status = mfgDetailDTO.getResult_status();
		System.out.println("MfgController checkApprove result_status->"+result_status);
		
		// 실패시 Map 2개
		if (mfgDetailDTO.getResult_status() == 0 ) {
		   List<WonCodeLackDTO> listWonCodeLackDTO = mfgDetailDTO.getListWonCodeLackDTO();
		   System.out.println("MfgController checkApprove listWonCodeLackDTO->"+listWonCodeLackDTO);
		   resultMap.put("listWonCodeLackDTO",listWonCodeLackDTO);
		   resultMap.put("result_status",result_status);
		   System.out.println("resultMap->"+resultMap);
		   
		} else { // 1이면 성공 
		
			System.out.println("MfgController checkApprove 1이면 성공");
		   // 경민 페리우스 로직
		   resultMap.put("result_status",result_status);
		   System.out.println("resultMap->"+resultMap);

	    }
		
	//	log.info(checkApprove);
		
		return resultMap;
	}
	
	
	// 생산 신청 승인 수정
	@PostMapping("/mfgApproveUpdate")
	public String mfgApproveUpdate(MfgDetailDTO mfgDetailDTO , Model model) {
		log.info("mfgDetailDTO->"+mfgDetailDTO);
		
		String magamNext = mfgService.magamNext();
	    String date = mfgDetailDTO.getMfg_due_date();
		
	    if(date == null || date.isEmpty()) {
	        mfgDetailDTO.setMfg_due_date(magamNext.replace("-" , "/"));
	    } else {
	    	mfgDetailDTO.setMfg_due_date(date.replace("-" , "/"));
	    }

	    // null이면 .isEmpty() 호출하면 NPE(NullPointerException)가 나니까 먼저 null 체크
	    if(mfgDetailDTO.getMfg_contents() != null && mfgDetailDTO.getMfg_contents().isEmpty()) {
	        mfgDetailDTO.setMfg_contents(null);
	    }
	   

//		if(date != null) {
//		    date = date.trim();
//		    if(date.isEmpty()) {
//		        mfgDetailDTO.setMfg_due_date(magamNext);
//		    } else {
//		        String[] parts = date.split("-");
//		        if(parts.length == 3) {
//		            String formatted = parts[0].substring(2) + "/" + parts[1] + "/" + parts[2];
//		            mfgDetailDTO.setMfg_due_date(formatted);
//		        } else {
//		            mfgDetailDTO.setMfg_due_date(magamNext);
//		        }
//		    }
//		} else {
//		    mfgDetailDTO.setMfg_due_date(magamNext);
//		}
		
		System.out.println("mfgDetailDTO->"+mfgDetailDTO);
	    
		mfgService.mfgApproveUpdate(mfgDetailDTO);
		
		
		List<MfgDetailDTO> mfgDetailList = mfgService.mfgDetail(mfgDetailDTO);
		log.info("mfgDetailList->"+mfgDetailList);
		model.addAttribute("mfgDetailList" , mfgDetailList);
		return "redirect:/km/mfgList";
	}
	
	// 생산 보고 등록
	@GetMapping("/mfgReportList")
	public String mfgReportList(MfgDetailDTO mfgDetailDTO , Model model) {
		
		int total = mfgService.mfgRpTotal();
		Paging page = new Paging(total, mfgDetailDTO.getCurrentPage());
		mfgDetailDTO.setStart(page.getStart());
		mfgDetailDTO.setEnd(page.getEnd());
		
		List<MfgDetailDTO> mfgReportList = mfgService.mfgReportList(mfgDetailDTO);
		log.info("mfgReportList->"+mfgReportList);
		
		model.addAttribute("page" , page);
		model.addAttribute("mfgReportList" , mfgReportList);
		
		return "mfg/mfgReportList";
	}
	
	// 생산 보고 폼
	@GetMapping("/mfgReportForm")
	public String mfgReportForm(MfgRpDTO mfgRpDTO , Model model) {
		log.info("mfgRpDTO->"+mfgRpDTO); // 생산 신청 코드 , 완제품 코드
		
		// 마감된 최근일을 가져온다
		String magamNext = mfgService.magamNext();
		System.out.println("magamNext->"+magamNext);
		
		List<MfgRpDTO> mfgRpList = mfgService.mfgReportForm(mfgRpDTO);
		System.out.println("mfgRpList->"+mfgRpList);
		
		if (!mfgRpList.isEmpty()) {
		    model.addAttribute("firstMfgRp", mfgRpList.get(0));
		}
		
		model.addAttribute("mfgRpDTO" , mfgRpDTO); // 생산 신청 코드 , 완제품 코드
		model.addAttribute("mfgRpList" , mfgRpList);
		model.addAttribute("magamNext" , magamNext);
		return "mfg/mfgReportForm";
	}
	
	// 생산 보고 등록
	@PostMapping("/mfgReportSubmit")
	public String mfgReportSubmit(@ModelAttribute MfgRpSaveDTO mfgRpSaveDTO , @AuthenticationPrincipal AccountDto emp) {
		log.info("mfgRpSaveDTO->"+mfgRpSaveDTO);
		
		// 로그인한 사원	
		mfgRpSaveDTO.getMfgRpDTO().setMfg_rp_reg_code(emp.getEmp_code());
		
		mfgService.mfgRpstatus(mfgRpSaveDTO.getMfgRpDTO());		// 생산신청상태 	변경
		mfgService.mfgRpSubmit(mfgRpSaveDTO.getMfgRpDTO());		// 생산보고 	등록
		mfgService.rpDetailSubmit(mfgRpSaveDTO.getDetails());	// 생산보고상세 	등록
		return "redirect:/km/mfgReportList";
	}
	
	// 생산 보고 상세보기
	@GetMapping("/mfgReportDetail")
	public String mfgReportDetail(MfgRpDTO mfgRpDTO , Model model) {
		log.info("mfgReportDetail mfgRpDTO->"+mfgRpDTO);
		List<MfgRpDetailDTO> mfgRpDetailDTO = mfgService.mfgReportDetail(mfgRpDTO);
		log.info("mfgReportDetail mfgRpDetailDTO->"+mfgRpDetailDTO);
		
		for(MfgRpDetailDTO detailDTO : mfgRpDetailDTO) {
			if(detailDTO.getMfg_end_date() != null) {
				detailDTO.setMfgEndDateStr(detailDTO.getMfg_end_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
			} else {
		        detailDTO.setMfgEndDateStr(""); // null이면 빈 문자열 처리
		    }
		}
		
		model.addAttribute("mfgRpDetailDTO" , mfgRpDetailDTO);
		return "mfg/mfgReportDetail";
	}
	
	// 생산보고 수정 폼
	@GetMapping("/mfgRpUpdateForm")
	public String mfgRpUpdateForm(MfgRpDTO mfgRpDTO , Model model) {
		System.out.println("MfgRpDTO->"+mfgRpDTO);
		
		// 마감된 최근일을 가져온다
		String magamNext = mfgService.magamNext();
		System.out.println("magamNext->"+magamNext);
		
		List<MfgRpDTO> mfgRpList = mfgService.mfgReportForm(mfgRpDTO);
		System.out.println("mfgRpList->"+mfgRpList);
		
		if (!mfgRpList.isEmpty()) {
		    model.addAttribute("firstMfgRp", mfgRpList.get(0));
		}
		
		model.addAttribute("mfgRpDTO" , mfgRpDTO); // 생산 신청 코드 , 완제품 코드
		model.addAttribute("mfgRpList" , mfgRpList);
		model.addAttribute("magamNext" , magamNext);
		return "mfg/mfgRpUpdateForm";
	}
	
	// 생산보고 수정
	@PostMapping("/mfgReportUpdate")
	public String mfgReportUpdate(MfgRpSaveDTO mfgRpSaveDTO) {
		System.out.println("MfgController mfgReportUpdate mfgRpSaveDTO->"+mfgRpSaveDTO);
		
		// 생산신청상태 	5번
		mfgService.mfgRpUpdate(mfgRpSaveDTO.getMfgRpDTO());		// 생산보고 	변경
		mfgService.rpDetailUpdate(mfgRpSaveDTO.getDetails());	// 생산보고상세 	변경
		
		return "redirect:/km/mfgReportList";
	}
	
}
	
