package com.oracle.oBootTodoApi01.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@PostMapping("/register1")
	public Map<String, Long> register1(ProductDTO productDTO) {
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
	
	
	@PutMapping("/modify/{pno}")
	public Map<String, String> modify(@PathVariable(name = "pno") Long pno, ProductDTO productDTO) {
		
		productDTO.setPno(pno);
		
		ProductDTO oldProductDTO = productService.get(pno);
		
		System.out.println("1. ProductController modify oldProductDTO-> " + oldProductDTO);
		// 기존의 파일들 ( 데이터베이스에 존재하는 파일들 - 수정 과정에서 삭제되었을 수 있음
		List<String> oldFileNames = oldProductDTO.getUploadFileNames();
		System.out.println("2. ProductController modify oldFileNames-> " + oldFileNames);
		
		// 새로 업로드 해야 하는 파일들
		List<MultipartFile> files = productDTO.getFiles();
		System.out.println("3. ProductController modify files-> " + files);
		
		// 새로 업로드되어서 만들어진 파일 이름들
		List<String> currentUploadFileNames = fileUtil.saveFiles(files);
		System.out.println("4. ProductController modify currentUploadFileNames-> " + currentUploadFileNames);
		
		//화면에서 변화 없이 계속 유지된 파일들
		List<String> uploadedFileNames = productDTO.getUploadFileNames();
		System.out.println("5. ProductController modify uploadedFileNames-> " + uploadedFileNames);
		
		//유지되는 파일들 + 새로 업로드된 파일 이름들이 저장해야 하는 파일 목록이 됨
		if(currentUploadFileNames != null || currentUploadFileNames.size()>0) {
			
			// 			다른 리스트의 요소들을 한꺼번에 현재 리스트에 추가해주는 기능
			uploadedFileNames.addAll(currentUploadFileNames); 
			//유지 + 새로 업로드한 걸 합쳐서 "최종 저장할 파일 목록"으로 만듦!
			System.out.println("6. ProductController modify uploadedFileNames-> " + uploadedFileNames);

		}
		
		// 1. DB 수정 작업
		productService.modify(productDTO);
		
		// ----------------------------------------------------
		// 2 . 지워야 하는 파일 목록 찾기 & 삭제
		if(oldFileNames != null && oldFileNames.size()>0) {
			
			//지워야하는 파일 목록 찾기 & 삭제
			//예전 파일들 중에서 지워져야 하는 파일이름들
			List<String> removeFiles = oldFileNames.stream()  // filter : 조건에 맞는 요소만 뽑기  / indexOf: 리스트 안에 값이 있으면 인덱스, 없으면 -1
												   .filter(fileName -> uploadedFileNames.indexOf(fileName) == -1)
												   .collect(Collectors.toList());
					
			System.out.println("7. ProductController modify removeFiles-> " + removeFiles);
			//실제 파일 삭제
			fileUtil.deleteFiles(removeFiles);
			//-----------------------------------------------------------------------	
		}
		return Map.of("RESULT","SUCCESS");
	}
	
	// ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스.
    // HttpStatus, HttpHeaders, HttpBody를 포함
	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> viewFileGET(@PathVariable(name = "fileName") String fileName) {
		System.out.println("ProductController viewFileGET fileName : " + fileName);
		return fileUtil.getFile(fileName);
	}
	
}
