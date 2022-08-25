package com.eauction.seller.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.eauction.seller.service.ProductCategoryService;

import reactor.core.publisher.Mono;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Value("#{${product.category.name}}")
	private Map<String, String> productCategoryName;

	@Override
	public Mono<ServerResponse> fetchProductCategories(ServerRequest serverRequest) {
		return ServerResponse.status(HttpStatus.OK).bodyValue(productCategoryName);
	}
}