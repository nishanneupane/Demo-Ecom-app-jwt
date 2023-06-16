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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishan.demo.dto.ApiResponse;
import com.nishan.demo.dto.ItemDto;
import com.nishan.demo.service.ItemService;

@RestController
@RequestMapping("/api/v1/item")
@CrossOrigin("http://localhost:3000/")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@PostMapping("/add/category/{categoryId}")
	public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto dto,@PathVariable Integer categoryId,Principal principal){
		return new ResponseEntity<ItemDto>(itemService.createItem(dto, categoryId,principal.getName()),HttpStatus.CREATED);
	}
	
	@PutMapping("/update/category/{categoryId}/item/{itemId}")
	public ResponseEntity<ItemDto> updateItem(@RequestBody ItemDto dto,@PathVariable Integer categoryId,@PathVariable Integer itemId,Principal principal){
		return new ResponseEntity<ItemDto>(itemService.updateItem(dto, categoryId, itemId, principal.getName()),HttpStatus.OK);
	}
	
	@GetMapping("/get/{itemId}")
	public ResponseEntity<ItemDto> getItem(@PathVariable Integer itemId){
		return new ResponseEntity<ItemDto>(itemService.getItem(itemId),HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<ItemDto>> getAllItems(){
		return new ResponseEntity<List<ItemDto>>(itemService.getAllItems(),HttpStatus.OK);
	}
	
	@GetMapping("/getMy/me")
	public ResponseEntity<List<ItemDto>> getMyitems(Principal principal){
		return new ResponseEntity<List<ItemDto>>(itemService.getByUser(principal.getName()),HttpStatus.OK);
	}
	
	@GetMapping("/get/category/{categoryId}")
	public ResponseEntity<List<ItemDto>> getMyitems(@PathVariable Integer categoryId){
		return new ResponseEntity<List<ItemDto>>(itemService.getByCategory(categoryId),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{itemId}")
	public ResponseEntity<ApiResponse> deleteItem(@PathVariable Integer itemId){
		itemService.deleteItems(itemId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("item deleted sucessfully ",true),HttpStatus.OK);
	}
}
