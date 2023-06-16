package com.nishan.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishan.demo.dto.ItemDto;
import com.nishan.demo.dto.UserDto;
import com.nishan.demo.entity.Category;
import com.nishan.demo.entity.Items;
import com.nishan.demo.entity.Role;
import com.nishan.demo.entity.User;
import com.nishan.demo.exception.ResourceNotFoundException;
import com.nishan.demo.exception.UserException;
import com.nishan.demo.repository.CategoryRepository;
import com.nishan.demo.repository.ItemRepository;
import com.nishan.demo.repository.UserRepository;

@Service
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;

	public ItemDto createItem(ItemDto itemDto, Integer categoryId, String username) {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category not found"));

		Items items = new Items();
		items.setName(itemDto.getName());
		items.setDescription(itemDto.getDescription());
		items.setPrice(itemDto.getPrice());
		items.setCreatedDateTime(LocalDateTime.now());
		items.setQuantity(itemDto.getQuantity());
		items.setStock(true);
		items.setUser(user);
		items.setCategory(category);
		items.setTotalCost(itemDto.getPrice() * itemDto.getQuantity());
		Items savedItem = itemRepository.save(items);

		ItemDto itemDto2 = modelMapper.map(savedItem, ItemDto.class);
		return itemDto2;
	}

	public ItemDto updateItem(ItemDto itemDto, Integer categoryId, Integer itemId, String username) {

		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category not found"));

		Items items = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("item not found"));
		items.setName(itemDto.getName());
		items.setDescription(itemDto.getDescription());
		items.setPrice(itemDto.getPrice());
		items.setUpdatedDateTime(LocalDateTime.now());
		items.setQuantity(itemDto.getQuantity());
		items.setStock(true);
		items.setUser(user);
		items.setCategory(category);
		items.setTotalCost(itemDto.getPrice()*itemDto.getQuantity());
		Items savedItem = itemRepository.save(items);

		ItemDto itemDto2 = modelMapper.map(savedItem, ItemDto.class);
		return itemDto2;
	}

	public ItemDto getItem(Integer itemId) {
		Items items = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("item not found"));
		items.setTotalCost(items.getPrice()*items.getQuantity());
		ItemDto itemDto = modelMapper.map(items, ItemDto.class);
		return itemDto;
	}

	public List<ItemDto> getAllItems() {
		
			List<Items> findAll = itemRepository.findAll();
			
			findAll.forEach(m -> m.setTotalCost(m.getPrice() * m.getQuantity()));
			List<ItemDto> collect = findAll.stream().map(m -> modelMapper.map(m, ItemDto.class))
					.collect(Collectors.toList());
			return collect;
	}

	public void deleteItems(Integer itemId) {
		itemRepository.deleteById(itemId);
	}

	public List<ItemDto> getByUser(String username) {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));
		List<Items> findByUser = itemRepository.findByUser(user);
		findByUser.forEach(m -> m.setTotalCost(m.getPrice() * m.getQuantity()));
		List<ItemDto> collect = findByUser.stream().map(m ->modelMapper.map(m, ItemDto.class)).collect(Collectors.toList());


		return collect;
	}

	public List<ItemDto> getByCategory(Integer categoryId) {
		Category cat = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category not found"));
		List<Items> findByCat = itemRepository.findByCategory(cat);
		findByCat.forEach(m -> m.setTotalCost(m.getPrice() * m.getQuantity()));
		List<ItemDto> collect = findByCat.stream().map(m ->modelMapper.map(m, ItemDto.class)).collect(Collectors.toList());

		return collect;

	}

}
