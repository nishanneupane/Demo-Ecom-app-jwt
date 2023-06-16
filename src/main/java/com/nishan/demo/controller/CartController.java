package com.nishan.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishan.demo.dto.ApiResponse;
import com.nishan.demo.dto.CartDto;
import com.nishan.demo.service.CartService;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	@PostMapping("/add/item/{itemId}")
	public ResponseEntity<CartDto> addToCart(@PathVariable Integer itemId,Principal principal){
		return new ResponseEntity<CartDto>(cartService.addToCart(principal.getName(), itemId),HttpStatus.CREATED);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<CartDto>> getCart(Principal principal){
		return new ResponseEntity<List<CartDto>>(cartService.getCartForUser(principal.getName()),HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<CartDto> getCart(@PathVariable Integer id){
		return new ResponseEntity<CartDto>(cartService.getCartById(id),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteCart(@PathVariable Integer id){
		cartService.deleteCart(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("cart removed ducessfully ",true),HttpStatus.OK);
	}

}
