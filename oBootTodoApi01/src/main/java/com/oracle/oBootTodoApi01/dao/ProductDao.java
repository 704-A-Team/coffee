package com.oracle.oBootTodoApi01.dao;

import java.util.List;

import com.oracle.oBootTodoApi01.dto.PageRequestDTO;
import com.oracle.oBootTodoApi01.dto.ProductDTO;

public interface ProductDao {
	int					totalProduct();
	List<ProductDTO>	listProduct(PageRequestDTO pageRequestDTO);

}
