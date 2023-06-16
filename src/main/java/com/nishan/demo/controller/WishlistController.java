package com.nishan.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishan.demo.dto.WishlistDto;
import com.nishan.demo.service.WishlistService;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController {
	@Autowired
	private WishlistService wishlistService;
	
	
	@PostMapping("/add/{itemId}")
	public ResponseEntity<WishlistDto> addToWishlist(@PathVariable Integer itemId,Principal principal){
		return new ResponseEntity<WishlistDto>(wishlistService.addToWishList(itemId, principal.getName()),HttpStatus.CREATED);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<WishlistDto>> getMyWishlist(Principal principal){
		return new ResponseEntity<List<WishlistDto>>(wishlistService.getWishlist(principal.getName()),HttpStatus.CREATED);
	}

}
