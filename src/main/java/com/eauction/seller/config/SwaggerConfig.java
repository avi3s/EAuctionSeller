package com.eauction.seller.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(
		title = "EAuction Sellers Dashboard",
		version = "1.0",
		description = "EAuction Management System for Sellers"
		))
public class SwaggerConfig {

}