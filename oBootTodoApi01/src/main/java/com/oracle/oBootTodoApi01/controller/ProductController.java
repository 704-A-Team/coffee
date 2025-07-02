package com.oracle.oBootTodoApi01.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.oBootTodoApi01.dto.PageRequestDTO;
import com.oracle.oBootTodoApi01.dto.PageResponseDTO;
import com.oracle.oBootTodoApi01.dto.ProductDTO;
import com.oracle.oBootTodoApi01.dto.TodoDTO;
import com.oracle.oBootTodoApi01.service.Paging;
import com.oracle.oBootTodoApi01.service.ProductService;
import com.oracle.oBootTodoApi01.util.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
	
	private final ProductService productService;
	private final CustomFileUtil fileUtil;
	
	@PostMapping("/register")
	public Map<String, Long> register(ProductDTO productDTO) {
		log.info("register: " + productDTO);
		// MultipartFile 해야 이미지를 받을 수 있다
		List<MultipartFile> files = productDTO.getFiles();
		// 이미지를 이미지리스트화
		
		// 파일을 업로드 File Upload
		List<String> uploadFileNames = fileUtil.saveFiles(files);
		productDTO.setUploadFileNames(uploadFileNames);
		log.info(uploadFileNames);
		
		// File Upload + Product 내용을 -> DB의 tbl_product에 INSERT ( 파일을 업로드 하고 나서 DB에 인서트 해야한다) 마이바티스에서 설명한 적 有
		
		Long pno = productService.register(productDTO);
		
		return Map.of("result", pno);
	}
	
	@GetMapping("/list")
	public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {
		log.info("list" + pageRequestDTO);
		int totalCount = productService.productTotal();
		System.out.println("ProductService list totalCount-> "+totalCount);
		
		// Paging 작업 3:10						현재 페이지이고 1이면
		Paging page = new Paging(totalCount, pageRequestDTO.getPage());
		pageRequestDTO.setStart(page.getStart()); // start 1번 부터
		pageRequestDTO.setEnd(page.getEnd()); // end 10 까지 보여줘라
		
		System.out.println("ProductController list() page after pageRequestDTO-> " + pageRequestDTO);
		log.info(pageRequestDTO);
		
		return productService.getList(pageRequestDTO);
		
	}
	
	// 상세보기
	@GetMapping("/{pno}")
	public ProductDTO read(@PathVariable(name = "pno") Long pno) {
		System.out.println("ProductController read() pno-> " + pno);
		return productService.get(pno);
	}
	
	@DeleteMapping("/remove/{pno}")
	public Map<String, String> remove(@PathVariable(name = "pno") Long pno) {
		
		System.out.println("ProductController remove() pno-> " + pno);
		// 삭제해야할 파일들 알아내기						 productDTO.getUploadFileNames()
		List<String> oldFileNames = productService.get(pno).getUploadFileNames();
		System.out.println("ProductController remove() oldFileNames-> " + oldFileNames);
		
		// 1. DB 날리기( upload )
		productService.remove(pno);
		// 2. file Server 데이터 삭제
		fileUtil.deleteFiles(oldFileNames);
		
		return Map.of("RESULT","SUCCESS");
	}
}
