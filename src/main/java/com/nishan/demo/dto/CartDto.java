package com.nishan.demo.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

private Integer id;
	
	@NotNull
	private Integer itemId;
	
	private UserDto user;
	
	private List<ItemDto> items;

}
