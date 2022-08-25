package com.eauction.seller.model;

import lombok.Data;

@Data
public class SellerModel {

	private String sellerId;
	private String emailId;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String pincode;
	private String mobileNo;
	private String jwt;
}