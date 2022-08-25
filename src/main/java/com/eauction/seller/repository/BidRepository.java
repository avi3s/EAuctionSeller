package com.eauction.seller.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.eauction.seller.entity.BidEntity;

import reactor.core.publisher.Flux;

public interface BidRepository extends ReactiveMongoRepository<BidEntity, String> {

	Flux<BidEntity> findAllByProductId(String productId, Sort sort);
}