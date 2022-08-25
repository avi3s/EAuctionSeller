package com.eauction.seller.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.eauction.seller.entity.ProductEntity;

public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {

}