package com.snorlax.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.snorlax.modal.Product;

@Mapper
public interface ProductMyBatisMapper {
	
	List<Product> searchByKeyword(
			@Param("storeId") Long storeId,
			@Param("query") String keyword);

}
