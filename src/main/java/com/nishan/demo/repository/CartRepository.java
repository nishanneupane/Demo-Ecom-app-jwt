package com.nishan.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nishan.demo.entity.Cart;
import com.nishan.demo.entity.User;

public interface CartRepository extends JpaRepository<Cart, Integer>{
	List<Cart> findByUser(User user);

}
