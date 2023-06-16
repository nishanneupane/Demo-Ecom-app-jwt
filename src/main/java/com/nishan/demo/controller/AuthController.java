package com.nishan.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishan.demo.dto.AuthToken;
import com.nishan.demo.dto.LoginRequest;
import com.nishan.demo.dto.UserDto;
import com.nishan.demo.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("http://localhost:3000/")
public class AuthController{
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		return new ResponseEntity<UserDto>(userService.register(userDto),HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthToken> registerUser(@RequestBody LoginRequest req){
		return new ResponseEntity<AuthToken>(userService.login(req),HttpStatus.CREATED);
	}

}
