package com.nishan.demo.dto;


import com.nishan.demo.entity.Maritial;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	@NotNull
	private String name;
	
	@NotNull
	private String username;
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	
	@NotNull
	private String contact;
	
	@NotNull
	private String address;
	
	@NotNull
	private Integer age;
	
	@NotNull
	private String sex;
	
	@Enumerated(EnumType.STRING)
	private Maritial maritialStatus;

}
