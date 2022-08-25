package com.eauction.seller.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.eauction.seller.entity.SellerEntity;

import reactor.core.publisher.Mono;

public interface SellerRepository extends ReactiveMongoRepository<SellerEntity, String> {

	Mono<SellerEntity> findSellerByEmailIdAndPassword(String emailId, String password);
}