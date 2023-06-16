package com.nishan.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDto {

	private Integer id;

	private UserDto user;

	private ItemDto item;

}
