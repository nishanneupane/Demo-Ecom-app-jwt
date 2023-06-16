package com.nishan.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nishan.demo.entity.User;
import com.nishan.demo.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer>{
	List<Wishlist> findByUser(User user);
}
