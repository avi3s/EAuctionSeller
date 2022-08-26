package com.eauction.seller.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.eauction.seller.entity.BidEntity;
import com.eauction.seller.entity.ProductEntity;

import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {

	Mono<BidEntity> findByProductId(String productId);
}