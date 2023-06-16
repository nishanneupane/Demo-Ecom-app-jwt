package com.nishan.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishan.demo.dto.CartDto;
import com.nishan.demo.dto.ItemDto;
import com.nishan.demo.dto.UserDto;
import com.nishan.demo.entity.Cart;
import com.nishan.demo.entity.Items;
import com.nishan.demo.entity.User;
import com.nishan.demo.exception.ResourceNotFoundException;
import com.nishan.demo.repository.CartRepository;
import com.nishan.demo.repository.ItemRepository;
import com.nishan.demo.repository.UserRepository;

import jakarta.mail.FetchProfile.Item;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ItemRepository itemRepository;

	public CartDto addToCart(String username, Integer itemId) {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("user not found "));
		Items items = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("item not found "));

		List<Items> itemlist = new ArrayList<>();
		itemlist.add(items);

		Cart cart = new Cart();
		cart.setItemId(itemId);
		cart.setItems(itemlist);
		cart.setUser(user);

		Cart savedCart = cartRepository.save(cart);

		return modelMapper.map(savedCart, CartDto.class);
	}

	public List<CartDto> getCartForUser(String username) {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("user not found "));
		List<Cart> cart = cartRepository.findByUser(user);



		return cart.stream().map(m->modelMapper.map(m, CartDto.class)).collect(Collectors.toList());
	}
	
	public CartDto getCartById(Integer id) {
		Cart cart = cartRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("cart not found "));
		
		return modelMapper.map(cart, CartDto.class);
	}
	
	public void deleteCart(Integer cartId) {
		cartRepository.deleteById(cartId);;
	}

}
