package com.nishan.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/")
	public ResponseEntity<String> sayHello(){
		return new ResponseEntity<String>("Welcome to our user service application",HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> sayHelloFromSecured(){
		return new ResponseEntity<String>("Hello from secured endpoint ",HttpStatus.OK);
	}

}
