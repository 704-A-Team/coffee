package com.oracle.coffee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.coffee.dao.SWProductDao;
import com.oracle.coffee.dto.ProductDto;
import com.oracle.coffee.dto.km.ProductImgDTO;
import com.oracle.coffee.util.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class SWProductServiceImpl implements SWProductService {
	
	private final SWProductDao 		productDao;
	private final CustomFileUtil	customFileUtil; 
	
	@Override
	public int wonProductSave(ProductDto productDto) {
		System.out.println("ProductServiceImpl wonProductSave start...");
		
		int wonProduct_code = productDao.wonProductSave(productDto);
		
		List<ProductImgDTO> productImgList = new ArrayList<>();
		for(int i = 0 ; i < productDto.getUploadFileNames().size(); i++) {
			String file_name = productDto.getUploadFileNames().get(i);
			
			ProductImgDTO pImgDTO = new ProductImgDTO();
			pImgDTO.setFile_name(file_name);
			pImgDTO.setOrd(i); // 0부터 시작
			pImgDTO.setProduct_code(productDto.getProduct_code());
			log.info("pImgDTO-> " + pImgDTO);
			productImgList.add(pImgDTO);
		}
		productDao.wonProductImgSave(productImgList);
		
		return wonProduct_code;
	}

	@Override
	public int totalProduct() {
		System.out.println("ProductServiceImpl totalProduct start...");
		
		int totalProduct = productDao.totalProduct();
		
		return totalProduct;
	}

	@Override
	public List<ProductDto> productList(ProductDto productDto) {
		System.out.println("ProductServiceImpl productList start...");
		
		List<ProductDto> productDtoList = productDao.productList(productDto);
		System.out.println("ProductServiceImpl productList productDtoList.size() : " + productDtoList.size());
		
		return productDtoList;
	}

	@Override
	public int totalWonProduct(ProductDto productDto) {
		System.out.println("ProductServiceImpl totalWonProduct start...");
		
		int totalWonProduct = productDao.totalWonProduct(productDto);
		
		return totalWonProduct;
	}

	@Override
	public ProductDto wonProductDetail(int product_code) {
		System.out.println("ProductServiceImpl wonProductDetail start...");
		
		ProductDto wonProductDetail = productDao.wonProductDetail(product_code);
		
		return wonProductDetail;
	}

	@Override
    public int wonProductModify(ProductDto productDto) {
        log.info("ProductServiceImpl wonProductModify start...");

        try {
            ProductDto oldProduct = productDao.wonProductDetail(productDto.getProduct_code());
            List<String> oldFileNames = oldProduct.getWonImgList().stream()
                .map(ProductImgDTO::getFile_name)
                .collect(Collectors.toList());

            List<String> formFileNames = productDto.getUploadFileNames();
            if (formFileNames == null || formFileNames.isEmpty()) {
                formFileNames = oldFileNames;
                productDto.setUploadFileNames(formFileNames);
                log.warn("formFileNames 누락 → oldFileNames로 대체 : " + oldFileNames);
            }
            log.info("formFileNames (유지): " + formFileNames);

            List<MultipartFile> uploadedFiles = productDto.getFiles();
            List<MultipartFile> newFiles = new ArrayList<>();

            if (uploadedFiles != null) {
                for (MultipartFile file : uploadedFiles) {
                    if (!file.isEmpty()) {
                        String originalName = file.getOriginalFilename();
                        boolean alreadyExists = formFileNames != null &&
                            formFileNames.stream().anyMatch(savedName -> savedName.endsWith("_" + originalName));
                        if (!alreadyExists) {
                            newFiles.add(file);
                        }
                    }
                }
            }

            List<String> savedNewFileNames = customFileUtil.saveFiles(newFiles);

            List<String> finalFileNames = new ArrayList<>();
            if (formFileNames != null) finalFileNames.addAll(formFileNames);
            if (savedNewFileNames != null) finalFileNames.addAll(savedNewFileNames);
            productDto.setUploadFileNames(finalFileNames);

            int result = productDao.wonProductModify(productDto);

            productDao.deleteProductImgs(productDto.getProduct_code());

            List<ProductImgDTO> imgList = new ArrayList<>();
            for (int i = 0; i < finalFileNames.size(); i++) {
                ProductImgDTO imgDto = new ProductImgDTO();
                imgDto.setProduct_code(productDto.getProduct_code());
                imgDto.setFile_name(finalFileNames.get(i));
                imgDto.setOrd(i);
                imgList.add(imgDto);
            }

            if (!imgList.isEmpty()) {
                productDao.insertProductImgs(imgList);
            }

            List<String> deleteList = new ArrayList<>();
            for (String old : oldFileNames) {
                if (!finalFileNames.contains(old)) {
                    deleteList.add(old);
                }
            }

            log.info("최종 넘어온 uploadFileNames : " + productDto.getUploadFileNames());
            customFileUtil.deleteFiles(deleteList);
            return result;

        } catch (Exception e) {
            log.error("ProductServiceImpl wonProductModify Exception : " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }




	@Override
	public void wonProductDelete(int product_code) {
		System.out.println("ProductServiceImpl wonProductDelete start...");
		
		ProductDto wonProductDetail = productDao.wonProductDetail(product_code);
		wonProductDetail.setProduct_isdel(1);
		
		productDao.wonProductDelete(wonProductDetail);
	}

	@Override
	public List<ProductDto> productIsList(int product_type) {
		log.info("ProductServiceImpl productAllList start...");
		
		return productDao.productIsList(product_type);
	}

}
