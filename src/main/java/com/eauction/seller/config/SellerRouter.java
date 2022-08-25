package com.eauction.seller.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.eauction.seller.model.BidDetailsModel;
import com.eauction.seller.model.LoginModel;
import com.eauction.seller.model.ProductCategoryModel;
import com.eauction.seller.model.ProductModel;
import com.eauction.seller.service.AddProductService;
import com.eauction.seller.service.DeleteProductService;
import com.eauction.seller.service.FetchBidService;
import com.eauction.seller.service.LoginService;
import com.eauction.seller.service.ProductCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration
public class SellerRouter implements WebFluxConfigurer {

	 @Bean
	 @RouterOperations({ 
		 // For Seller's Login
		 @RouterOperation(path = "/api/v1/seller/login", 
				 produces = {MediaType.APPLICATION_JSON_VALUE}, 
				 consumes = {MediaType.APPLICATION_JSON_VALUE},
				 method = RequestMethod.POST, 
				 beanClass = LoginService.class, 
				 beanMethod = "login",
                 operation = @Operation(operationId = "login",
                 responses = {@ApiResponse(responseCode = "200", description = "Login Successful", content = @Content(schema = @Schema(implementation = String.class))),
                		 	  @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))},
                 requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = LoginModel.class))))),
	 })
    public RouterFunction<ServerResponse> loginRoute(LoginService loginService) {
        return route()
                .nest(path("/api/v1/seller/login"), builder ->
                        builder
                                .POST("", RequestPredicates.accept(MediaType.APPLICATION_JSON), loginService::login))
                				.build();
	}
	 
	 @Bean
	 @RouterOperations({ 
		 // For Adding Products
		 @RouterOperation(path = "/api/v1/seller/add-product", 
				 produces = {MediaType.APPLICATION_JSON_VALUE}, 
				 method = RequestMethod.POST, 
				 beanClass = AddProductService.class, 
				 beanMethod = "addProduct",
                 operation = @Operation(operationId = "addProduct",
                 responses = {@ApiResponse(responseCode = "201", description = "Products Added Successfully", content = @Content(schema = @Schema(implementation = String.class))),
                		 	  @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))},
                 requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = ProductModel.class))))),
		 })
	    public RouterFunction<ServerResponse> addProductRoute(AddProductService addProductService) {
	        return route()
	                .nest(path("/api/v1/seller/add-product"), builder ->
	                        builder
	                                .POST("", RequestPredicates.accept(MediaType.APPLICATION_JSON), addProductService::addProduct))
	                				.build();
		}
	 
	 @Bean
	 @RouterOperations({ 
		// For Fetching Product Categories
		@RouterOperation(path = "/api/v1/seller/show-product-categories", 
				 produces = {MediaType.APPLICATION_JSON_VALUE}, 
				 method = RequestMethod.GET, 
				 beanClass = ProductCategoryService.class, 
				 beanMethod = "fetchProductCategories",
		         operation = @Operation(operationId = "fetchProductCategories",
		         responses = {@ApiResponse(responseCode = "200", description = "Fetch All Product Categories", content = @Content(schema = @Schema(implementation = ProductCategoryModel.class))) })),
			 
		 })
	    public RouterFunction<ServerResponse> fetchProductCategoriesRoute(ProductCategoryService productCategoryService) {
	        return route()
	                .nest(path("/api/v1/seller/show-product-categories"), builder ->
	                        builder
	                                .GET("", productCategoryService::fetchProductCategories))
	                				.build();
		}
	 
	 @Bean
	 @RouterOperations({ 
		// For Showing Bids By Product Id
		 @RouterOperation(path = "/api/v1/seller/show-bids/{productId}", 
		 produces = {MediaType.APPLICATION_JSON_VALUE }, 
		 method = RequestMethod.GET, 
		 beanClass = FetchBidService.class, 
		 beanMethod = "fetchBidsByProductId", 
		 operation = @Operation(operationId = "fetchBidsByProductId", 
		 responses = {@ApiResponse(responseCode = "200", description = "Get Bids By Product Id", content = @Content(schema = @Schema(implementation = BidDetailsModel.class))),
				 	  @ApiResponse(responseCode = "404", description = "Bids not found by given Product Id") }, 
		 parameters = {@Parameter(in = ParameterIn.PATH, name = "id") })),
		 })
	    public RouterFunction<ServerResponse> fetchBidsRoute(FetchBidService fetchBidService) {
	        return route()
	                .nest(path("/api/v1/seller/show-bids"), builder ->
	                        builder
	                                .GET("/{productId}", RequestPredicates.accept(MediaType.APPLICATION_JSON), fetchBidService::fetchBidsByProductId))
	                				.build();
		}
	 
	 @Bean
	 @RouterOperations({ 
		// For Deleting Product By Product Id
		 @RouterOperation(path = "/api/v1/seller/delete/{productId}", 
		 produces = {MediaType.APPLICATION_JSON_VALUE }, 
		 method = RequestMethod.DELETE, 
		 beanClass = DeleteProductService.class, 
		 beanMethod = "deleteProduct", 
		 operation = @Operation(operationId = "deleteProduct", 
		 responses = {@ApiResponse(responseCode = "200", description = "Product Deleted Successfully", content = @Content(schema = @Schema(implementation = String.class))),
				 	  @ApiResponse(responseCode = "404", description = "Product not found by given Id") }, 
		 parameters = {@Parameter(in = ParameterIn.PATH, name = "id") })),
			 
		 })
	    public RouterFunction<ServerResponse> deleteProductRoute(DeleteProductService deleteProductService) {
	        return route()
	                .nest(path("/api/v1/seller/delete"), builder ->
	                        builder
	                                .DELETE("/{productId}", deleteProductService::deleteProduct))
	                				.build();
		}
}