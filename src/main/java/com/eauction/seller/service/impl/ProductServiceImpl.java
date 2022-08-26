package com.eauction.seller.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.eauction.seller.entity.ProductEntity;
import com.eauction.seller.helper.ProductException;
import com.eauction.seller.helper.Util;
import com.eauction.seller.model.ProductModel;
import com.eauction.seller.repository.BidRepository;
import com.eauction.seller.repository.ProductRepository;
import com.eauction.seller.service.AddProductService;
import com.eauction.seller.service.DeleteProductService;

import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements AddProductService,DeleteProductService {

	@Autowired
	private Validator validator;
	
	@Autowired
    private Util util;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private BidRepository bidRepository;
	
	@Autowired
	@Lazy
	private ModelMapper modelMapper;
	
	@Value("${product.not.found}")
	private String productMissing;
	
	@Override
	public Mono<ServerResponse> addProduct(ServerRequest serverRequest) {
		return serverRequest.bodyToMono(ProductModel.class)
                .doOnNext(this::validate)
                .flatMap(productModel -> productRepository.save(transform(productModel, ProductEntity.class))
                .flatMap(productEntity -> ServerResponse.status(HttpStatus.CREATED).bodyValue(productEntity)));
	}
	
	@Override
	public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {
		
		var productId = serverRequest.pathVariable("productId");
		return productRepository.findByProductId(productId)
				.flatMap(product -> productRepository.deleteById(productId))
				.then(ServerResponse.noContent().build());
	}
	
	private void validate(ProductModel productModel) {

	       var constraintViolations = validator.validate(productModel);
	       if (constraintViolations.size() > 0) {
	           var errorMessage = constraintViolations.stream()
	                   .map(ConstraintViolation::getMessage)
	                   .sorted()
	                   .collect(Collectors.joining(", "));
	           throw new ProductException(errorMessage);
	       }
	}
	
	private ProductEntity transform(ProductModel from, Class<ProductEntity> valueType) {
		
		if (Objects.nonNull(from)) {
			ProductEntity productEntity = modelMapper.map(from, valueType);
			return productEntity;
		} else {
			return null;
		}
	}
}
