package com.nishan.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nishan.demo.dto.ItemDto;
import com.nishan.demo.entity.Category;
import com.nishan.demo.entity.Items;
import com.nishan.demo.entity.User;

public interface ItemRepository extends JpaRepository<Items, Integer>{
	
	List<Items> findByUser(User user);
	List<Items> findByCategory(Category category);

}
