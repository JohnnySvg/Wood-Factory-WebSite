package com.lemn.sitelemn.dto;

import jakarta.validation.constraints.NotBlank;

public class OrderDTO {
	  @NotBlank
	  private String address;

	  @NotBlank
	  private String phone;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	  
	}
