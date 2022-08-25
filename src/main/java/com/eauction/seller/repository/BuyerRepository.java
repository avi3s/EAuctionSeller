package com.eauction.seller.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.eauction.seller.entity.BuyerEntity;

import reactor.core.publisher.Mono;

public interface BuyerRepository extends ReactiveMongoRepository<BuyerEntity, String> {

	Mono<BuyerEntity> findBuyerByEmailIdAndPassword(String emailId, String password);
}
