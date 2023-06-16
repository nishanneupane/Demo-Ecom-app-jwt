package com.nishan.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDtoItem {
	private String productName;
	private int quantity;
	private double price;
	private long productId;
	private Integer userId;
}
