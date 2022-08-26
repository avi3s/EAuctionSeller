package com.eauction.seller.model;

import java.time.LocalDateTime;

import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ProductModel {

	private String productId;
	private String productCategoryId;
	private String productCategoryName;
	private String sellerId;
	
	@NotBlank(message = "{productName.null.message}")
	@Size(min = 5, max = 30, message = "{productName.invalid.message}")
    private String productName;
	
    private String shortDescription;
    private String detailedDescription;
    
    @NotBlank(message = "{startingPrice.null.message}")
	@Digits(message = "{startingPrice.invalid.message}", fraction = 2, integer = 10)
    private String startingPrice;
    
    @FutureOrPresent(message = "{bidEndDateTime.invalid.message}")
    private LocalDateTime bidEndDateTime;
}