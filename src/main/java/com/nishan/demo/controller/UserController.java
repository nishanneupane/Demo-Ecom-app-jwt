package com.nishan.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishan.demo.dto.ApiResponse;
import com.nishan.demo.dto.UserDto;
import com.nishan.demo.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("http://localhost:3000/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PutMapping("/update")
	public ResponseEntity<UserDto> updateUserDetails(@RequestBody UserDto dto,Principal principal){
		return new ResponseEntity<UserDto>(userService.updateUser(dto, principal.getName()),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{email}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable String email,Principal principal){
		userService.deleteUser(email, principal.getName());
		return new ResponseEntity<ApiResponse>(new ApiResponse("user Deleted sucessfully",true),HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<UserDto>> getAllUser(){
		return new ResponseEntity<List<UserDto>>(userService.findAllUser(),HttpStatus.OK);
	}
	
	@GetMapping("/get/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
		return new ResponseEntity<UserDto>(userService.getByEmail(email),HttpStatus.OK);
	}
	
	@GetMapping("get/name/{name}")
	public ResponseEntity<List<UserDto>> findByName(@PathVariable String name,Principal principal){
		return new ResponseEntity<List<UserDto>>(userService.findByName(name, principal.getName()),HttpStatus.OK);
	}
	
	@GetMapping("get/address/{address}")
	public ResponseEntity<List<UserDto>> findByAddress(@PathVariable String address,Principal principal){
		return new ResponseEntity<List<UserDto>>(userService.findByAddress(address, principal.getName()),HttpStatus.OK);
	}
	
	@GetMapping("get/age/{age}")
	public ResponseEntity<List<UserDto>> findByAge(@PathVariable Integer age,Principal principal){
		return new ResponseEntity<List<UserDto>>(userService.findByAge(age, principal.getName()),HttpStatus.OK);
	}
	
	@GetMapping("get/search/{query}")
	public ResponseEntity<List<UserDto>> search(@PathVariable String query,Principal principal){
		return new ResponseEntity<List<UserDto>>(userService.search(query, principal.getName()),HttpStatus.OK);
	}

}
