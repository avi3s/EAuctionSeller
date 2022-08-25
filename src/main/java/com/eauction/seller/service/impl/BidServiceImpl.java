package com.eauction.seller.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.eauction.seller.service.FetchBidService;

import reactor.core.publisher.Mono;

@Service
public class BidServiceImpl implements FetchBidService {

	@Override
	public Mono<ServerResponse> fetchBidsByProductId(ServerRequest serverRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
