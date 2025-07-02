package com.oracle.oBootTodoApi01.service;

import com.oracle.oBootTodoApi01.dto.PageRequestDTO;
import com.oracle.oBootTodoApi01.dto.PageResponseDTO;
import com.oracle.oBootTodoApi01.dto.ProductDTO;

public interface ProductService {
	Long 						register(ProductDTO productDTO);
	int 						productTotal();
	PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);
	ProductDTO 					get(Long pno);
	void 						remove(Long pno);
}
