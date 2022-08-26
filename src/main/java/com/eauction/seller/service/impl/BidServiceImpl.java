package com.eauction.seller.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.eauction.seller.entity.BidEntity;
import com.eauction.seller.repository.BidRepository;
import com.eauction.seller.service.FetchBidService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BidServiceImpl implements FetchBidService {

	@Autowired
	private BidRepository bidRepository;
	
	@Override
	public Mono<ServerResponse> fetchBidsByProductId(ServerRequest serverRequest) {
		
		var productId = serverRequest.pathVariable("productId");
		var bids = bidRepository.findAllByProductId(productId, Sort.by(Sort.Direction.DESC, "bidAmount"));
		return buildBidsResponse(bids);
	}
	
    private Mono<ServerResponse> buildBidsResponse(Flux<BidEntity> bidEntities) {
        return ServerResponse.ok().body(bidEntities, BidEntity.class);
    }
}