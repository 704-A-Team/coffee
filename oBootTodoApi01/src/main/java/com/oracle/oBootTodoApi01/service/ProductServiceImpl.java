package com.oracle.oBootTodoApi01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootTodoApi01.dao.ProductDao;
import com.oracle.oBootTodoApi01.domain.Product;
import com.oracle.oBootTodoApi01.domain.ProductImage;
import com.oracle.oBootTodoApi01.dto.PageRequestDTO;
import com.oracle.oBootTodoApi01.dto.PageResponseDTO;
import com.oracle.oBootTodoApi01.dto.ProductDTO;
import com.oracle.oBootTodoApi01.dto.ProductImageDTO;
import com.oracle.oBootTodoApi01.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;	// JPA
	private final ProductDao		productDao;			// MyBatis
	
	@Override
	public Long register(ProductDTO productDTO) {
		// Dto 를 Entity로 바꿔야 한다
		Product product = dtoToEntity(productDTO);
		Product saveProduct = productRepository.save(product);
		
		return saveProduct.getPno();
	}
	
	// Dto 를 Entity로 바꿔야 한다   : Entity는 DML 용도이다 , 전달의 목적이 아니다
	private Product dtoToEntity(ProductDTO productDTO) {
		Product product = Product.builder()
								 .pno(productDTO.getPno())
								 .pname(productDTO.getPname())
								 .pdesc(productDTO.getPdesc())
								 .price(productDTO.getPrice())
								 .keyword(productDTO.getKeyword())
								 .delFlag(productDTO.isDelFlag())
								 .build()
								 ;
		// 위에가 Entity로 바꾸는 것이고 밑에는 업로드 처리 관련이다 11:03
		
		// 업로드 처리가 끝난 파일들의 이름 리스트
		List<String> uploadfileNames = productDTO.getUploadFileNames();
		if(uploadfileNames == null) return product;
		
		// Entity에게 uploadName명을 넘겨줌 --> List<ProductImage> imageList 누적
		uploadfileNames.stream()
					   .forEach(uploadfileName-> {
						   product.addImageString(uploadfileName);
					   });
		
		return product;
	}

	@Override
	public int productTotal() {
		int totalCount = productDao.totalProduct();
		return totalCount;
	}

	@Override
	public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {
		log.info("ProductServiceImpl getList()");
		
		System.out.println("ProductServiceImpl getList() pageRequestDTO-> " + pageRequestDTO);
		
		List<ProductDTO> dtoProductList = productDao.listProduct(pageRequestDTO);
		System.out.println("ProductServiceImpl getList() dtoProductList-> " + dtoProductList);
		int totalCount = productDao.totalProduct();
		// Build 사용이유 : 유지보수 용이 
		// 1. 많은 필드(특히 선택적 필드 많을 때)
		// 2. 객체 생성 과정이 복잡할 때
		// 3. 가독성(불변 객채)
		// 간단 객체 --> 생성자
		return PageResponseDTO.<ProductDTO>withAll()
							  .dtoList(dtoProductList)
							  .totalCount(totalCount)
							  .pageRequestDTO(pageRequestDTO)
							  .build()
							  ;
	}

	@Override
	public ProductDTO get(Long pno) {
		Optional<Product> mayProduct = productRepository.selectOne(pno);
		Product product = mayProduct.orElseThrow();
		ProductDTO productDTO = entityToDTO(product);
		
		return productDTO;
	}
	
	// Entity -> DTO
	private ProductDTO entityToDTO(Product product) {

		ProductDTO productDTO = ProductDTO.builder()
										  .pno(product.getPno())
										  .pname(product.getPname())
										  .price(product.getPrice())
										  .pdesc(product.getPdesc())
										  .keyword(product.getKeyword())
										  .build()
										  ;
		
		// 이미지
		//                         이미지리스트를 가져올 수 있는 이유 : 도메인에서 element해뒀기 때문에 내부적으로 가져올 수 있다
		List<ProductImage> imageList = product.getImageList();
		
		if(imageList == null || imageList.size() == 0) return productDTO;
		
		List<String> fileNameList = 
						imageList.stream()
								 .map(productImage -> productImage.getFileName())
								 .toList()
								 ;
		// 모든 파일의 이미지를 보내준다
		productDTO.setUploadFileNames(fileNameList);
		
		return productDTO;
	}

	@Override
	public void remove(Long pno) {
		// 삭제가 아닌 업데이트 ( 목적: 실적이 사라지는 것을 방지 )
		productRepository.updateToDelete(pno, true);
	}

	@Override
	public void modify(ProductDTO productDTO) {
		// 컨트롤러는 이미지 업로드 / 서비스는 DB 이미지 업로드
		Optional<Product> maybeProduct = productRepository.findById(productDTO.getPno());
		
		Product product = maybeProduct.orElseThrow();
		
		// change pname, pdesc, price
		product.changePname(productDTO.getPname());
		product.changePdesc(productDTO.getPdesc());
		product.changePrice(productDTO.getPrice());	
		product.changeKeyword(productDTO.getKeyword());
		
		//upload File -- clear first
		product.clearList();
		// DB-->Image String
		List<String> uploadFileNames = productDTO.getUploadFileNames();
		
		if(uploadFileNames != null || uploadFileNames.size()> 0) {
			uploadFileNames.stream()
						   .forEach(uploadName -> product.addImageString(uploadName));
		}
		productRepository.save(product);
	}

}
