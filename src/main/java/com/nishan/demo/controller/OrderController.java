package com.nishan.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishan.demo.dto.CartDtoItem;
import com.nishan.demo.dto.StripeResponsee;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
	
	@Autowired
	private com.nishan.demo.service.OrderService orderService;
	
	//stripe checkout api
	
	@PostMapping("/create-checkout-session")
	private ResponseEntity<com.nishan.demo.dto.StripeResponsee> checkoutList(@RequestBody List<CartDtoItem> checkoutItemDtos) throws StripeException{
		
		com.stripe.model.checkout.Session session=orderService.createSession(checkoutItemDtos);
		
		StripeResponsee response=new StripeResponsee(session.getId());
		
		return new ResponseEntity<StripeResponsee>(response,HttpStatus.OK);
		
	}

}
