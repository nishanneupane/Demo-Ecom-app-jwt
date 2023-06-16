package com.nishan.demo.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

	private Integer id;
	@NotNull
	private String name;
	
	private String image;
	@NotNull
	private String description;
	@NotNull
	private Integer quantity;
	@NotNull
	private Double price;
	private Double totalCost;
	private boolean stock;
	
	private CategoryDto category;
	
	private UserDto user;
}
