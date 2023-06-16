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
import com.nishan.demo.dto.CategoryDto;
import com.nishan.demo.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin("http://localhost:3000/")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
		return new ResponseEntity<CategoryDto>(categoryService.createCategory(categoryDto),HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId,Principal principal){
		return new ResponseEntity<CategoryDto>(categoryService.updateCategory(categoryDto, categoryId, principal.getName()),HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId){
		return new ResponseEntity<CategoryDto>(categoryService.findById(categoryId),HttpStatus.CREATED);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		return new ResponseEntity<List<CategoryDto>>(categoryService.viewAllCategory(),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId,Principal principal){
		categoryService.deleteCategory(categoryId, principal.getName());
		return new ResponseEntity<ApiResponse>(new ApiResponse("category sucessfully deleted ",true),HttpStatus.CREATED);
	}
	

}
