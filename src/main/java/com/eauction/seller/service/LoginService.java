package com.eauction.seller.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface LoginService {

	Mono<ServerResponse> login(ServerRequest serverRequest);
}
