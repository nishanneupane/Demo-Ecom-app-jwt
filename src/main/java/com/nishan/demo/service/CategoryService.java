package com.nishan.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishan.demo.dto.CategoryDto;
import com.nishan.demo.entity.Category;
import com.nishan.demo.entity.Role;
import com.nishan.demo.exception.CategoryException;
import com.nishan.demo.exception.CustomException;
import com.nishan.demo.repository.CategoryRepository;
import com.nishan.demo.repository.UserRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;

	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = new Category();
		category.setCreatedDateTime(LocalDateTime.now());
		category.setDescription(categoryDto.getDescription());
		category.setName(categoryDto.getName());

		Category savedCategory = categoryRepository.save(category);

		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId, String username) {
		if (userRepository.findByEmail(username).orElseThrow().getRole() == Role.ADMIN) {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new CategoryException("category not found "));
			category.setCreatedDateTime(LocalDateTime.now());
			category.setDescription(categoryDto.getDescription());
			category.setName(categoryDto.getName());

			Category savedCategory = categoryRepository.save(category);

			return modelMapper.map(savedCategory, CategoryDto.class);
		} else {
			throw new CustomException("you are not a admin");
		}
	}
	
	public void deleteCategory(Integer categoryId, String username) {
		if (userRepository.findByEmail(username).orElseThrow().getRole() == Role.ADMIN) {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new CategoryException("category not found "));
			categoryRepository.delete(category);
		} else {
			throw new CustomException("you are not a admin");
		}
	}
	
	public List<CategoryDto> viewAllCategory(){
		List<Category> findAll = categoryRepository.findAll();
		return findAll
				.stream().map(cat->modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
	}
	
	public CategoryDto findById(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new CategoryException("category not found with this id"));
		return modelMapper.map(category, CategoryDto.class);
	}
}
