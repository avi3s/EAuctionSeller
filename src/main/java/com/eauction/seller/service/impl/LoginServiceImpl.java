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

import com.eauction.seller.entity.SellerEntity;
import com.eauction.seller.helper.LoginException;
import com.eauction.seller.helper.Util;
import com.eauction.seller.model.SellerModel;
import com.eauction.seller.model.LoginModel;
import com.eauction.seller.repository.SellerRepository;
import com.eauction.seller.service.LoginService;

import reactor.core.publisher.Mono;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
    private Util util;
	
	@Autowired
	@Lazy
	private ModelMapper modelMapper;
	
	@Value("${invalid.user}")
	private String invalidUser;
	
	@Override
	public Mono<ServerResponse> login(ServerRequest serverRequest) {
		return serverRequest.bodyToMono(LoginModel.class)
			   .doOnNext(this::validate)
			   .flatMap(seller -> transform(sellerRepository.findSellerByEmailIdAndPassword(seller.getEmailId(), seller.getPassword()), SellerModel.class))
			   .flatMap(sellerModel ->  ServerResponse.status(HttpStatus.OK).bodyValue(sellerModel))
			   .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(invalidUser));
	}
	
	private void validate(LoginModel loginModel) {

	       var constraintViolations = validator.validate(loginModel);
	       if (constraintViolations.size() > 0) {
	           var errorMessage = constraintViolations.stream()
	                   .map(ConstraintViolation::getMessage)
	                   .sorted()
	                   .collect(Collectors.joining(", "));
	           throw new LoginException(errorMessage);
	       } else {
	    	   util.printLog(loginModel, "Incoming Request");
	       }
	}
	
	private Mono<SellerModel> transform(Mono<SellerEntity> from, Class<SellerModel> valueType) {

		SellerModel sellerModelTest = new SellerModel();// Delete this line once DB contains value
		sellerModelTest.setEmailId("avirup.pal@gmail.com");// Delete this line once DB contains value
		sellerModelTest.setFirstName("Avirup");// Delete this line once DB contains value
		sellerModelTest.setLastName("Pal");// Delete this line once DB contains value
		sellerModelTest.setSellerId("1");// Delete this line once DB contains value
		sellerModelTest.setJwt(util.createJwt(sellerModelTest));// Delete this line once DB contains value
		return from.flatMap(be -> {
								util.printLog(be, "Coming From Database");
								if (Objects.nonNull(be)) {
									SellerModel sellerModel = modelMapper.map(from, valueType);
									sellerModel.setJwt(util.createJwt(sellerModel));
									return Mono.just(sellerModel);
								} else {
									return null;
								}
							})
					.switchIfEmpty(Mono.just(sellerModelTest));// Delete this line once DB contains value
	}
}