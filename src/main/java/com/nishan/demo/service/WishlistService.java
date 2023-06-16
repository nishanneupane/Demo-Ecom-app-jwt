package com.nishan.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishan.demo.dto.ItemDto;
import com.nishan.demo.dto.UserDto;
import com.nishan.demo.dto.WishlistDto;
import com.nishan.demo.entity.Items;
import com.nishan.demo.entity.User;
import com.nishan.demo.entity.Wishlist;
import com.nishan.demo.exception.ResourceNotFoundException;
import com.nishan.demo.repository.ItemRepository;
import com.nishan.demo.repository.UserRepository;
import com.nishan.demo.repository.WishlistRepository;

@Service
public class WishlistService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WishlistRepository wishlistRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ModelMapper modelMapper;

	public WishlistDto addToWishList(Integer itemId, String username) {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("user not found with this email"));
		Items items = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("items not found with this id"));

		Wishlist wishlist = new Wishlist();
		wishlist.setItems(items);
		wishlist.setUser(user);
		Wishlist savedWishlist = wishlistRepository.save(wishlist);

		WishlistDto map = modelMapper.map(savedWishlist, WishlistDto.class);
		map.setUser(modelMapper.map(user, UserDto.class));
		map.setItem(modelMapper.map(items, ItemDto.class));

		return map;

	}

	public List<WishlistDto> getWishlist(String username) {
	    User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found with this email"));
	    List<Wishlist> list = wishlistRepository.findByUser(user);

	    List<WishlistDto> collect = list.stream().map(wishlist -> {
	        WishlistDto dto = modelMapper.map(wishlist, WishlistDto.class);
	        dto.setItem(modelMapper.map(wishlist.getItems(), ItemDto.class));
	        return dto;
	    }).collect(Collectors.toList());

	    return collect;
	}


}
